package com.caubarreaux.service;

import com.caubarreaux.entity.Anagram;
import com.caubarreaux.entity.Word;
import com.caubarreaux.entity.WordIO;
import com.caubarreaux.exception.BadRequestException;
import com.caubarreaux.exception.BaseApiException;
import com.caubarreaux.integration.WordRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import sun.misc.ASCIICaseInsensitiveComparator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import lombok.Data;

import static org.springframework.util.StringUtils.isEmpty;

/**
 * User: ross
 * Date: 5/19/17
 * Time: 10:35 AM
 */

@Data
@Service
public class WordServiceImpl implements WordService {

    @Autowired
    private WordRepository wordRepository;

    /**
     * Returns all words from the database in ascending order based on <tt>value</> property .
     * @return  a WordIO object containing a list of words (Strings).
     * @exception BaseApiException if an error occurs.
     */
    @Override
    public WordIO getAllWords() throws BaseApiException {
        WordIO result = new WordIO();
        List<Word> foundList = new ArrayList<>();
        List<String> wordValues = new ArrayList<>();

        foundList = wordRepository.findAll(new Sort(Sort.Direction.ASC, "value"));

        for (Word word : foundList) {
            wordValues.add(word.getValue());
        }
        result.setWords(wordValues);
        return result;
    }

    /**
     * Finds all words that are anagrams of the supplied word. All words that are of the same length
     * as the given word are returned from the database. A map holds a count of each character
     * that makes up the supplied word. All words of the same length are fetched from the database
     * and stored into a list. The list is then iterated over and compared against a copy of the
     * original word map by decrementing the count of each character found in the word. If the map
     * is found to have all values of 0 then the word is an anagram and added to the results list
     * to be returned.
     * @param value (required) cannot be null or empty.
     * @param aLimit restricts the number of anagrams returned. If less than several limit will be ignored.
     * @return  An Anagram object containing a list of strings representing related anagrams.
     * @exception  BadRequestException  if the <tt>value</> is empty.
     */
    @Override
    public Anagram getAnagramsOfWord(String value, String aLimit) throws BaseApiException {
        if (isEmpty(value)) {
            throw new BadRequestException("No input provided");
        }

        List<Word> results = new ArrayList<>();

        int[] masterCharMap = new int[256];

        int maxLength = value.length();

        Integer limit;
        try {
            limit = Integer.parseInt(aLimit);
        } catch (NumberFormatException e) {
            limit = -1;
        }

        for (char c : value.toCharArray()) {
            masterCharMap[c]++;
        }

        Set<Word> allWords = new HashSet<>();
        allWords.addAll(wordRepository.findByLength(maxLength));

        int numOfResults;
        if (limit > 0) {
            numOfResults = limit;
        } else {
            numOfResults = allWords.size();
        }

        for (Word possibleAnagram : allWords) {
            int[] charMap = masterCharMap.clone();
            for (char c : possibleAnagram.getValue().toLowerCase().toCharArray()) {
                charMap[c]--;
            }

            if (isAnagram(charMap) && !possibleAnagram.getValue().equals(value)) {
                results.add(possibleAnagram);
            }
            if (numOfResults == results.size()) {
                break;
            }
        }

        Anagram anagram = new Anagram();

        for (Word word : results) {
            anagram.getAnagrams().add(word.getValue());
        }

        anagram.getAnagrams().sort(new ASCIICaseInsensitiveComparator());
        return anagram;
    }

    /**
     * Saves all given words into the database.
     * @param wordStrings (required) cannot be null or empty.
     * @exception  BadRequestException  if <tt>wordsStrings</> is empty.
     */
    @Override
    public void insertAllWords(List<String> wordStrings) throws BaseApiException {
        List<Word> words = new ArrayList<>();

        if (CollectionUtils.isEmpty(wordStrings)) {
            throw new BadRequestException("No input provided");
        }

        for (String wordString : wordStrings) {
            if (!isEmpty(wordString)) {
                words.add(new Word(wordString, wordString.length()));
            }
        }

        saveAllWords(words);
    }

    /**
     * Deletes provided word from the database.
     * @param value (required) cannot be null or empty.
     * @exception  BadRequestException  if value is empty.
     */
    @Override
    public void deleteWord(String value) throws BaseApiException {
        if (isEmpty(value)) {
            throw new BadRequestException("No input provided");
        }

        Word word = wordRepository.findByValue(value);
        wordRepository.delete(word);
    }

    /**
     * Deletes <b>ALL</> words from the database.
     * @exception  BaseApiException  if an error occurs.
     */
    @Override
    public void deleteAllWords() throws BaseApiException {
        wordRepository.deleteAll();
    }

    /**
     * Saves all provided words in the database. If a duplicate is found, it will be ignored.
     * @param words (required) cannot be null or empty.
     * @exception  BadRequestException  if the <tt>words</> is empty or null.
     */
    private void saveAllWords(List<Word> words) throws BaseApiException {
        if (CollectionUtils.isEmpty(words)) {
            throw new BadRequestException("No input provided");
        }

        try {
            wordRepository.save(words);
        } catch (DuplicateKeyException e) {
            // Do Nothing
        }
    }

    /**
     * Determines if a given map is represents a valid anagram. If all values in the map are equal
     * to <tt>0</>, then the map is valid.
     * @param map (required) is an int map representing a count for each character in a word.
     * @return <tt>true</tt> only if the map contains all values of 0.
     */
    private boolean isAnagram(int[] map) {
        for (int i = 0; i < map.length; i++) {
            if (map[i] != 0) {
                return false;
            }
        }
        return true;
    }
}
