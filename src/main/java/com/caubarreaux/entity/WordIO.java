package com.caubarreaux.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * User: ross
 * Date: 5/23/17
 * Time: 6:52 AM
 */

@Data
public class WordIO {

    public WordIO() {
        words = new ArrayList<>();
    }

    public WordIO(List<String> words) {
        this.words = words;
    }

    private List<String> words;
}
