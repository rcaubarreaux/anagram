package com.caubarreaux.controller;

import com.caubarreaux.entity.Anagram;
import com.caubarreaux.entity.WordIO;
import com.caubarreaux.exception.BaseApiException;
import com.caubarreaux.service.WordService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * User: ross
 * Date: 5/19/17
 * Time: 10:36 AM
 */

@RestController
public class WordController {

    @Autowired
    private WordService wordService;

    @ApiOperation(
        notes = "Returns all words",
        value = "Get all words",
        response = WordIO.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @RequestMapping(value = {"/words"}, method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<WordIO> getAllWords() throws BaseApiException {
        return new ResponseEntity<WordIO>(wordService.getAllWords(), HttpStatus.OK);
    }

    @ApiOperation(
            notes = "Adds given word(s) to the collection",
            value = "Add Word(s)"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Created"),
            @ApiResponse(code = 400, message = "No input provided"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @RequestMapping(value = {"/words"}, method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity insertWord(@RequestBody WordIO words) throws BaseApiException {
        wordService.insertAllWords(words.getWords());
        return new ResponseEntity(HttpStatus.CREATED);
    }

    @ApiOperation(
            notes = "Delete given word from the collection",
            value = "Delete a Word"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "No input provided"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @RequestMapping(value = "/words/{word}", method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity removeWord(@PathVariable String word) throws BaseApiException {
        wordService.deleteWord(word);
        return new ResponseEntity(HttpStatus.OK);
    }

    @ApiOperation(
            notes = "Delete all words from the collection",
            value = "Delete all Words"
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 204, message = "No Content"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @RequestMapping(value = {"/words"}, method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity removeWords() throws BaseApiException {
        wordService.deleteAllWords();
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @ApiOperation(
            notes = "Retrieves a list of \"Anagrams\" related to the given word. Number of results can be limited using the \"limit\" query param",
            value = "Get Anagrams",
            response = Anagram.class
    )
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 400, message = "No input provided"),
            @ApiResponse(code = 404, message = "The resource you were trying to reach is not found"),
            @ApiResponse(code = 500, message = "Internal server error")
    })
    @RequestMapping(name = "anagrams", value = "/anagrams/{word}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Anagram> getAllAnagrams(@PathVariable("word") String word, @RequestParam(required = false) String limit) throws BaseApiException {
        Anagram results = wordService.getAnagramsOfWord(word, limit);
        return new ResponseEntity<Anagram>(results, HttpStatus.OK);
    }
}
