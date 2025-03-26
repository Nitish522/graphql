package com.example.demo.models;

import java.util.Arrays;
import java.util.List;

public record Book (String id, String name, int pageCount, String authorId) {

    private static final List<Book> books = Arrays.asList(
            new Book("1", "Effective Java", 416, "author-1"),
            new Book("2", "Hitchhiker's Guide to the Galaxy", 208, "author-2"),
            new Book("3", "Down Under", 436, "author-3")
    );

    public static Book getById(String id) {
        return books.stream()
                .filter(book -> book.id().equals(id))
                .findFirst()
                .orElse(null);
    }

    public static List<Book> getAll() {
        return books;
    }
}