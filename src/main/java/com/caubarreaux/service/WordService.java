package com.caubarreaux.service;

import com.caubarreaux.entity.Anagram;
import com.caubarreaux.entity.WordIO;
import com.caubarreaux.exception.BaseApiException;

import java.util.List;

/**
 * User: ross
 * Date: 5/25/17
 * Time: 10:56 AM
 */

public interface WordService {
    WordIO getAllWords() throws BaseApiException;
    Anagram getAnagramsOfWord(String value, String aLimit) throws BaseApiException;
    void insertAllWords(List<String> wordStrings) throws BaseApiException;
    void deleteWord(String value) throws BaseApiException;
    void deleteAllWords() throws BaseApiException;
}
