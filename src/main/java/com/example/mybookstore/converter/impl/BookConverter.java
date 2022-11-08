package com.example.mybookstore.converter.impl;

import com.example.mybookstore.converter.Converter;
import com.example.mybookstore.data.BookData;
import com.example.mybookstore.model.Book;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@Component
public class BookConverter implements Converter<Book, BookData> {

    @Override
    public BookData convert(Book source) {
        BookData data = new BookData();

        if (isNull(source)) {
            return data;
        }
        data.setTitle(source.getTitle());
        data.setDescription(source.getDescription());
        data.setPublishingDate(source.getPublishingDate());
        data.setPrice(source.getPrice());
        data.setAuthorName(source.getAuthor().getName());
        data.setAuthorEmail(source.getAuthor().getEmail());
        return data;
    }
}
