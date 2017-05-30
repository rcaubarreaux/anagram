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

    // #TODO: Remove
//    private static <E> Collection<E> toCollection(Iterable<E> iter) {
//        Collection<E> list = new ArrayList<E>();
//        for (E item : iter) {
//            list.add(item);
//        }
//        return list;
//    }
}
