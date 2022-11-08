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
public class AuthorReverseConverter implements Converter<AuthorData, Author> {
    @Override
    public Author convert(AuthorData source) {
        Author author = new Author();
        author.setName(source.getName());
        author.setEmail(source.getEmail());
        author.setBirthDate(source.getBirthDate());
        return author;
    }
}
