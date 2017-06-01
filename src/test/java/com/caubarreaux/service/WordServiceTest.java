package com.caubarreaux.service;

import com.caubarreaux.entity.Anagram;
import com.caubarreaux.entity.Word;
import com.caubarreaux.entity.WordIO;
import com.caubarreaux.exception.BadRequestException;
import com.caubarreaux.exception.BaseApiException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.ArgumentCaptor;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertNull;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.isA;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * User: ross
 * Date: 5/20/17
 * Time: 10:54 AM
 */

public class WordServiceTest extends BaseServiceTest {

    private WordServiceImpl wordService;

    @Before
    public void setup() {
        wordService = new WordServiceImpl();
        wordService.setWordRepository(wordRepository);
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testInsertOfEmptyWordList() throws BaseApiException {
        List<String> wordList = new ArrayList<>();

        thrown.expect(BadRequestException.class);
        thrown.expectMessage(is("No input provided"));

        wordService.insertAllWords(wordList);
    }

    @Test
    public void testInsertOfOneWord() throws BaseApiException {
        List<String> wordList = new ArrayList<>();
        wordList.add("care");

        wordService.insertAllWords(wordList);

        final ArgumentCaptor<List<Word>> listCaptor = ArgumentCaptor.forClass((Class) List.class);
        verify(wordRepository, times(1)).save(listCaptor.capture());

        List<Word> results = listCaptor.getValue();
        assertNotNull(results);
        assertThat(results.size(), is(1));

        Word result = results.get(0);
        assertNotNull(result);
        assertThat(result, isA(Word.class));
        assertThat(result.getValue(), is("care"));
        assertThat(result.getLength(), is(4));
    }

    @Test
    public void testInsertOfTwoWords() throws BaseApiException {
        List<String> wordList = new ArrayList<>();
        wordList.add("care");
        wordList.add("share");

        wordService.insertAllWords(wordList);

        final ArgumentCaptor<List<Word>> listCaptor = ArgumentCaptor.forClass((Class) List.class);
        verify(wordRepository, times(1)).save(listCaptor.capture());

        List<Word> results = listCaptor.getValue();
        assertNotNull(results);
        assertThat(results.size(), is(2));

        Word result = results.get(0);
        assertNotNull(result);
        assertThat(result, isA(Word.class));
        assertThat(result.getValue(), is("care"));
        assertThat(result.getLength(), is(4));

        result = results.get(1);
        assertNotNull(result);
        assertThat(result, isA(Word.class));
        assertThat(result.getValue(), is("share"));
        assertThat(result.getLength(), is(5));
    }

    @Test
    public void testGetAllWords() throws BaseApiException {
        List<Word> words = new ArrayList<>();
        words.add(new Word("care", 4));
        words.add(new Word("share", 5));

        when(wordRepository.findAll(new Sort(Sort.Direction.ASC, "value"))).thenReturn(words);

        WordIO results = wordService.getAllWords();

        verify(wordRepository, times(1)).findAll(new Sort(Sort.Direction.ASC, "value"));

        assertNotNull(results);
        assertNotNull(results.getWords());
        assertThat(results.getWords().size(), is(2));

        assertNotNull(results.getWords().get(0));
        assertThat(results.getWords().get(0), is("care"));

        assertNotNull(results.getWords().get(1));
        assertThat(results.getWords().get(1), is("share"));
    }

    @Test
    public void testGetAllWordsIsEmpty() throws BaseApiException {
        List<Word> words = new ArrayList<>();

        when(wordRepository.findAll(new Sort(Sort.Direction.ASC, "value"))).thenReturn(words);

        WordIO results = wordService.getAllWords();

        verify(wordRepository, times(1)).findAll(new Sort(Sort.Direction.ASC, "value"));

        assertNotNull(results);
        assertNotNull(results.getWords());
        assertThat(results.getWords().size(), is(0));
    }

    @Test
    public void testDeleteWithNullValue() throws BaseApiException {
        thrown.expect(BadRequestException.class);
        thrown.expectMessage(is("No input provided"));

        wordService.deleteWord(null);
    }

    @Test
    public void testDeleteWithEmptyValue() throws BaseApiException {
        thrown.expect(BadRequestException.class);
        thrown.expectMessage(is("No input provided"));

        wordService.deleteWord("");
    }

    @Test
    public void testDeleteOneWord() throws BaseApiException {
        String word = "care";

        wordService.deleteWord(word);

        final ArgumentCaptor<Word> captor = ArgumentCaptor.forClass((Class) Word.class);
        verify(wordRepository, times(1)).delete(captor.capture());

        Word result = captor.getValue();

        assertNull(result);
    }

    @Test
    public void testDeleteAllWords() throws BaseApiException {
        wordService.deleteAllWords();
        verify(wordRepository, times(1)).deleteAll();
    }

    @Test
    public void testGetAnagramsWithNullValue() throws BaseApiException {
        thrown.expect(BadRequestException.class);
        thrown.expectMessage(is("No input provided"));

        wordService.getAnagramsOfWord(null, null);
    }

    @Test
    public void testGetAnagramsWithEmptyValue() throws BaseApiException {
        thrown.expect(BadRequestException.class);
        thrown.expectMessage(is("No input provided"));

        wordService.getAnagramsOfWord("", null);
    }

    @Test
    public void testGetAnagramsForWordWithNoLimit() throws BaseApiException {
        String word = "care";
        List<Word> anagrams = new ArrayList<>();
        anagrams.add(new Word("Acer", 4));

        when(wordRepository.findByLength(4)).thenReturn(anagrams);

        Anagram results = wordService.getAnagramsOfWord(word, null);

        verify(wordRepository, times(1)).findByLength(4);

        assertNotNull(results);
        assertNotNull(results.getAnagrams());
        assertThat(results.getAnagrams().size(), is(1));

        assertNotNull(results.getAnagrams().get(0));
        assertThat(results.getAnagrams().get(0), is("Acer"));
    }

    @Test
    public void testGetAnagramsForWordWithLimitOfOne() throws BaseApiException {
        String word = "care";
        List<Word> anagrams = new ArrayList<>();
        anagrams.add(new Word("Acer", 4));
        anagrams.add(new Word("race", 4));

        when(wordRepository.findByLength(4)).thenReturn(anagrams);

        Anagram results = wordService.getAnagramsOfWord(word, "1");

        verify(wordRepository, times(1)).findByLength(4);

        assertNotNull(results);
        assertNotNull(results.getAnagrams());
        assertThat(results.getAnagrams().size(), is(1));

        assertNotNull(results.getAnagrams().get(0));
        assertThat(results.getAnagrams().get(0), is("Acer"));
    }

    @Test
    public void testGetAnagramsForWordWithLimitOfThree() throws BaseApiException {
        String word = "care";
        List<Word> anagrams = new ArrayList<>();
        anagrams.add(new Word("Acer", 4));
        anagrams.add(new Word("race", 4));

        when(wordRepository.findByLength(4)).thenReturn(anagrams);

        Anagram results = wordService.getAnagramsOfWord(word, "3");

        verify(wordRepository, times(1)).findByLength(4);

        assertNotNull(results);
        assertNotNull(results.getAnagrams());
        assertThat(results.getAnagrams().size(), is(2));

        assertNotNull(results.getAnagrams().get(0));
        assertThat(results.getAnagrams().get(0), is("Acer"));
        assertThat(results.getAnagrams().get(1), is("race"));
    }
}
