package com.example.mybookstore.service;

import com.example.mybookstore.data.BookData;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface BookService {
    Flux<BookData> getAllBooks();

    Mono<String> addBook(BookData bookData);

    Mono<String> removeBook(BookData bookData);
}
