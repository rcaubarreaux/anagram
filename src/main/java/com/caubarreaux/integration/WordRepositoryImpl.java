package com.caubarreaux.integration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

/**
 * User: ross
 * Date: 5/20/17
 * Time: 10:02 PM
 */

public class WordRepositoryImpl implements WordRepositoryCustom {

    @Autowired
    private WordRepository repository;

    @Autowired
    private MongoTemplate mongoTemplate;
}
