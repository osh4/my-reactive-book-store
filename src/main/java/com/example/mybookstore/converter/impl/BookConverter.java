package com.example.mybookstore.converter.impl;

import com.example.mybookstore.converter.Converter;
import com.example.mybookstore.data.BookData;
import com.example.mybookstore.model.Book;
import com.example.mybookstore.model.BookStoreUser;
import com.example.mybookstore.repository.UserRepository;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@Component
public class BookConverter implements Converter<Book, BookData> {
  private final UserRepository userRepository;

    public BookConverter(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

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
        if (nonNull(source.getAuthorEmail())) {
            userRepository.findById(source.getAuthorEmail())
                    .subscribe(author -> setAuthorData(data, author));
        }
        return data;
    }

    private static void setAuthorData(BookData data, BookStoreUser author) {
        data.setAuthorEmail(author.getEmail());
        data.setAuthorName(author.getName());
    }
}
