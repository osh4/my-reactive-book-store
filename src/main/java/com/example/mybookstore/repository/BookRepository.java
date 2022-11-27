package com.example.mybookstore.repository;

import com.example.mybookstore.model.Book;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


public interface BookRepository extends R2dbcRepository<Book, String> {
    Flux<Book> findAllByAuthorEmail(String authorEmail);
}
