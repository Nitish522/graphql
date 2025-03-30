package com.example.demo.models;

import com.example.demo.staticdtata.StaticData;
import com.example.demo.utils.JsonParser;

import java.util.List;

public record Author(String id, String name, String rating) {

    private static final List<Author> authors = JsonParser.parseToList(StaticData.author, Author.class);

    public static Author getById(String id) {
        return authors.stream()
                .filter(author -> author.id().equals(id))
                .findFirst()
                .orElse(null);
    }
}
