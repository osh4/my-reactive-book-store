package com.example.mybookstore.converter.impl;

import com.example.mybookstore.converter.Converter;
import com.example.mybookstore.data.AuthorData;
import com.example.mybookstore.data.UserType;
import com.example.mybookstore.model.BookStoreUser;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class AuthorReverseConverter implements Converter<AuthorData, Mono<BookStoreUser>> {
    @Override
    public Mono<BookStoreUser> convert(AuthorData source) {
        return Mono.just(BookStoreUser.builder().name(source.getName())
                .email(source.getEmail())
                .birthDate(source.getBirthDate())
                .type(UserType.AUTHOR.toString())
                .build());
    }
}
