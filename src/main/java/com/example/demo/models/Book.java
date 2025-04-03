package com.example.demo.models;

import com.example.demo.staticdtata.StaticData;
import com.example.demo.utils.JsonParser;

import java.util.List;
import java.util.stream.Collectors;

import static com.example.demo.utils.CommonUtils.getSubList;

public record Book(String id, String bookName, String authId) {

    private static final List<Book> books = JsonParser.parseToList(StaticData.books, Book.class);

    public static Book getById(String id) {
        return books.stream()
                .filter(book -> book.id().equals(id))
                .findFirst()
                .orElse(null);
    }

    public static List<Book> getAll() {
        return books;
    }

    public static Connection<Book> getAll(int page, Integer pageSize) {

        pageSize = pageSize != null ? pageSize : 10;

        int offSet = (page - 1) * pageSize;

        var lsOfBooks = getSubList(books, offSet, pageSize);

        List<Edge<Book>> customerEdges = lsOfBooks.stream()
                .map(customer -> new Edge<>(String.valueOf(customer.id), customer))
                .collect(Collectors.toList());

        String startCursor = customerEdges.isEmpty() ? null : customerEdges.get(0).cursor();
        String endCursor = customerEdges.isEmpty() ? null : customerEdges.get(customerEdges.size() - 1).cursor();

        PageInfo pageInfo = new PageInfo(startCursor, endCursor, books.size(), offSet, customerEdges.size());
        return new Connection<>(pageInfo, customerEdges);
    }



}


