package com.caubarreaux.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

/**
 * User: ross
 * Date: 5/19/17
 * Time: 10:27 AM
 */

@Data
@Document
@CompoundIndexes({
        @CompoundIndex(name = "index_value", unique = true, def = "{'value' : 1, 'length' : 1}")
})
public class Word {

    @Id
    private String id;

    private String value;

    private int length;

    public Word() {
    }

    public Word(String value, int length) {
        this.value = value;
        this.length = length;
    }
}
