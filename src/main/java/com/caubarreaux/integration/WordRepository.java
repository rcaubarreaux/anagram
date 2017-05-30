package com.caubarreaux.integration;

import com.caubarreaux.entity.Word;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * User: ross
 * Date: 5/20/17
 * Time: 9:58 PM
 */

@Repository
public interface WordRepository extends MongoRepository<Word, String>, WordRepositoryCustom {

    Word findByValue(String value);

    List<Word> findByLength(int length);
}
