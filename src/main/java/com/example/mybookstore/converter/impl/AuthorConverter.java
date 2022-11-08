package com.example.mybookstore.converter.impl;

import com.example.mybookstore.converter.Converter;
import com.example.mybookstore.data.AuthorData;
import com.example.mybookstore.data.BookData;
import com.example.mybookstore.model.Author;
import com.example.mybookstore.model.Book;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class AuthorConverter implements Converter<Author, AuthorData> {
    private final Converter<Book, BookData> bookConverter;

    public AuthorConverter(@Lazy Converter<Book, BookData> bookConverter) {
        this.bookConverter = bookConverter;
    }

    @Override
    public AuthorData convert(Author source) {
        AuthorData data = new AuthorData();
        if (isNull(source)) {
            return data;
        }
        data.setEmail(source.getEmail());
        data.setName(source.getName());
        data.setBirthDate(source.getBirthDate());
        data.setBooks(bookConverter.convertAll(source.getBooks()));
        return data;
    }
}
