package com.example.mybookstore.service;

import com.example.mybookstore.data.BookData;

import java.util.List;

public interface BookService {
    List<BookData> getAllBooks();

    void addBook(BookData bookData);

    void removeBook(BookData bookData);
}
