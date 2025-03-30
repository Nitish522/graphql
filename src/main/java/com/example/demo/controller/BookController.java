package com.example.demo.controller;

import com.example.demo.models.Author;
import com.example.demo.models.Book;
import com.example.demo.models.Connection;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.ContextValue;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class BookController {

    @QueryMapping
    public Book bookById(@Argument String id, @ContextValue String authHeader) {
        System.out.println("Authorization Header: " + authHeader);
        return Book.getById(id);
    }

    @QueryMapping
    public List<Book> books() {
        return Book.getAll();
    }

    @QueryMapping
    public Connection<Book> booksByPage(@Argument Integer first,@Argument Integer pageSize) {
        return Book.getAll(first, pageSize);
    }

//    @QueryMapping
//    public Connection<Book> booksByPage(Integer first, String after, Integer last, String before) {
//        return Book.getAll(first);
//    }

    @SchemaMapping
    public Author author(Book book) {
        return Author.getById(book.authId());
    }
}
