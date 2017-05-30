package com.caubarreaux.integration;

import com.caubarreaux.application.AnagramApplication;
import com.caubarreaux.entity.Anagram;
import com.caubarreaux.entity.WordIO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertThat;

/**
 * User: ross
 * Date: 5/21/17
 * Time: 7:22 AM
 */


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        classes = {AnagramApplication.class})
@TestPropertySource(
        locations = "classpath:integrationtest.properties")
public class WordIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Before
    public void setUp() {
        WordIO input = new WordIO();
        input.getWords().add("read");
        input.getWords().add("dear");
        input.getWords().add("dare");

        ResponseEntity responseEntity = restTemplate.postForEntity("/words.json", input, WordIO.class);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
    }

    @After
    public void cleanUp() {
        restTemplate.delete("/words.json", new HashMap<>());
    }

    @Test
    public void testAddingWords() {
        WordIO input = new WordIO();
        input.getWords().add("care");

        ResponseEntity responseEntity = restTemplate.postForEntity("/words.json", input, WordIO.class);
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());

        ResponseEntity<WordIO> entity = restTemplate.getForEntity("/words.json", WordIO.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());

        assertNotNull(entity.getBody());
        assertNotNull(entity.getBody().getWords());

        assertThat(entity.getBody().getWords().size(), is(4));
        assertThat(entity.getBody().getWords().get(0), is("care"));
        assertThat(entity.getBody().getWords().get(1), is("dare"));
        assertThat(entity.getBody().getWords().get(2), is("dear"));
        assertThat(entity.getBody().getWords().get(3), is("read"));
    }

    @Test
    public void testFetchingAnagrams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("word","read");

        ResponseEntity<Anagram> responseEntity = restTemplate.getForEntity("/anagrams/{word}", Anagram.class, params);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getAnagrams());

        assertThat(responseEntity.getBody().getAnagrams().size(), is(2));
        assertThat(responseEntity.getBody().getAnagrams().get(0), is("dare"));
        assertThat(responseEntity.getBody().getAnagrams().get(1), is("dear"));
    }

    // test_fetching_anagrams_with_limit
    @Test
    public void testFetchingAnagramsWithLimit() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("word","read");
        params.put("limit","1");

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString("/anagrams/{word}")
                .queryParam("limit", "1");

        ResponseEntity<Anagram> responseEntity =
                restTemplate.exchange(builder.buildAndExpand(params).toUri(), HttpMethod.GET, null, Anagram.class);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getAnagrams());

        assertThat(responseEntity.getBody().getAnagrams().size(), is(1));
    }

    @Test
    public void testFetchForWordWithNoAnagrams() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("word","bob");

        ResponseEntity<Anagram> responseEntity = restTemplate.getForEntity("/anagrams/{word}", Anagram.class, params);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        assertNotNull(responseEntity.getBody());
        assertNotNull(responseEntity.getBody().getAnagrams());

        assertThat(responseEntity.getBody().getAnagrams().size(), is(0));
    }

    // test_deleting_all_words
    @Test
    public void testDeletingAllWords() {

        restTemplate.delete("/words.json", new HashMap<>());

        ResponseEntity<WordIO> entity = restTemplate.getForEntity("/words.json", WordIO.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());
        assertNotNull(entity.getBody());
        assertNotNull(entity.getBody().getWords());
        assertThat(entity.getBody().getWords().size(), is(0));
    }


    @Test
    public void testDeletingSingleWord() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("word","dare");

        restTemplate.delete("/words/{word}", params);

        ResponseEntity<WordIO> entity = restTemplate.getForEntity("/words.json", WordIO.class);
        assertEquals(HttpStatus.OK, entity.getStatusCode());

        assertNotNull(entity.getBody());
        assertNotNull(entity.getBody().getWords());

        assertThat(entity.getBody().getWords().size(), is(2));
    }
}
