package com.caubarreaux.entity;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

/**
 * User: ross
 * Date: 5/22/17
 * Time: 3:14 PM
 */

@Data
public class Anagram {

    private List<String> anagrams;

    public Anagram() {
        anagrams = new ArrayList<>();
    }
}
