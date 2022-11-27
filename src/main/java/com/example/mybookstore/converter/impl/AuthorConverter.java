package com.example.mybookstore.converter.impl;

import com.example.mybookstore.converter.Converter;
import com.example.mybookstore.data.AuthorData;
import com.example.mybookstore.data.BookData;
import com.example.mybookstore.model.Book;
import com.example.mybookstore.model.BookStoreUser;
import com.example.mybookstore.repository.BookRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.LinkedHashSet;

import static java.util.Objects.isNull;

@Component
public class AuthorConverter implements Converter<BookStoreUser, AuthorData> {
    private final BookRepository bookRepository;
    private final Converter<Book, BookData> bookConverter;

    public AuthorConverter(BookRepository bookRepository, @Lazy Converter<Book, BookData> bookConverter) {
        this.bookRepository = bookRepository;
        this.bookConverter = bookConverter;
    }

    @Override
    public AuthorData convert(BookStoreUser source) {
        AuthorData authorData = AuthorData.builder()
                .email(source.getEmail())
                .name(source.getName())
                .birthDate(source.getBirthDate())
                .build();

        bookRepository.findAllByAuthorEmail(source.getEmail())
                .map(book -> setBooksToAuthorData(book, authorData))
                .subscribe();
        return authorData;
    }

    private AuthorData setBooksToAuthorData(Book book, AuthorData data) {
        if (isNull(data.getBooks())) {
            data.setBooks(new LinkedHashSet<>());
        }
        data.getBooks().add(bookConverter.convert(book));
        return data;
    }
}
