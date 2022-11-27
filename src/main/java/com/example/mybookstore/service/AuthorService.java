package com.example.mybookstore.service;

import com.example.mybookstore.data.AuthorData;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public interface AuthorService {
    Flux<AuthorData> getAllAuthors();

    Mono<String> addAuthor(AuthorData author);

    Mono<String> removeAuthor(AuthorData author, boolean removeBooks);
}
