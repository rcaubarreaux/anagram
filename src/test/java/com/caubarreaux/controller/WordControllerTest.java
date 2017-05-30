package com.caubarreaux.controller;

import com.caubarreaux.entity.Anagram;
import com.caubarreaux.entity.WordIO;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * User: ross
 * Date: 5/25/17
 * Time: 11:33 AM
 */

public class WordControllerTest extends BaseControllerTest{

    private ObjectMapper mapper = new ObjectMapper();

    @Before
    public void setUp() throws Exception {
        super.setUp();
    }

    @Test
    public void getAllWordsService() throws Exception {
        WordIO input = new WordIO();
        input.getWords().add("care");
        input.getWords().add("share");

        String body = mapper.writeValueAsString(input);

        WordIO output = new WordIO();
        output.getWords().add("care");
        output.getWords().add("share");

        when((wordService.getAllWords())).thenReturn(output);
        this.mockMvc.perform(get("/words").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(containsString(body)));
    }

    @Test
    public void removeWordService() throws Exception {
        this.mockMvc.perform(delete("/words/care").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void removeAllWordsService() throws Exception {
        this.mockMvc.perform(delete("/words").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    public void getAllAnagramsService() throws Exception {
        String word = "care";

        Anagram output = new Anagram();
        output.getAnagrams().add("Acer");
        output.getAnagrams().add("race");

        String result = mapper.writeValueAsString(output);

        when((wordService.getAnagramsOfWord(word, null))).thenReturn(output);
        this.mockMvc.perform(get("/anagrams/" + word).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(containsString(result)));
    }

    @Test
    public void getAllAnagramsWithLimitOfOneService() throws Exception {
        String word = "care";

        Anagram output = new Anagram();
        output.getAnagrams().add("race");

        String result = mapper.writeValueAsString(output);

        when((wordService.getAnagramsOfWord(word, "1"))).thenReturn(output);
        this.mockMvc.perform(get("/anagrams/" + word).param("limit", "1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(content().string(containsString(result)));
    }

    @Test
    public void insertAllWordsService() throws Exception {
        WordIO input = new WordIO();
        input.getWords().add("care");
        input.getWords().add("share");

        String result = mapper.writeValueAsString(input);
        this.mockMvc.perform(post("/words").content(result).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
}
