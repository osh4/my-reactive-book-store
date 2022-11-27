package com.example.mybookstore.converter.impl;

import com.example.mybookstore.converter.Converter;
import com.example.mybookstore.data.AuthorData;
import com.example.mybookstore.model.BookStoreUser;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AuthorReverseConverter implements Converter<AuthorData, Mono<BookStoreUser>> {
    @Override
    public Mono<BookStoreUser> convert(AuthorData source) {
        return Mono.just(new BookStoreUser())
                .map(author -> populateFromSource(author, source));
    }

    private BookStoreUser populateFromSource(BookStoreUser author, AuthorData source) {
        author.setName(source.getName());
        author.setEmail(source.getEmail());
        author.setBirthDate(source.getBirthDate());
        author.setType("AUTHOR");
        return author;
    }
}
