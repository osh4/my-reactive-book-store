package com.example.mybookstore.converter.impl;

import com.example.mybookstore.converter.Converter;
import com.example.mybookstore.data.BookData;
import com.example.mybookstore.model.Book;
import com.example.mybookstore.model.BookStoreUser;
import com.example.mybookstore.repository.UserRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class BookConverter implements Converter<Book, BookData> {
  private final UserRepository userRepository;

    public BookConverter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public BookData convert(Book source) {
        BookData data = BookData.builder()
                .title(source.getTitle())
                .description(source.getDescription())
                .publishingDate(source.getPublishingDate())
                .price(source.getPrice())
                .build();

        Mono.just(source.getAuthorEmail())
                .flatMap(userRepository::findById)
                .map(author -> setAuthorData(data, author))
                .subscribe();

        return data;
    }

    private BookStoreUser setAuthorData(BookData data, BookStoreUser author) {
        data.setAuthorEmail(author.getEmail());
        data.setAuthorName(author.getName());
        return author;
    }
}
