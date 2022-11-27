package com.example.mybookstore.converter.impl;

import com.example.mybookstore.converter.Converter;
import com.example.mybookstore.data.AuthorData;
import com.example.mybookstore.data.BookData;
import com.example.mybookstore.model.Book;
import com.example.mybookstore.model.BookStoreUser;
import com.example.mybookstore.repository.UserRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Component
public class BookReverseConverter implements Converter<BookData, Mono<Book>> {
    private final Converter<AuthorData, Mono<BookStoreUser>> authorReverseConverter;
    private final UserRepository userRepository;

    public BookReverseConverter(Converter<AuthorData, Mono<BookStoreUser>> authorReverseConverter, UserRepository userRepository) {
        this.authorReverseConverter = authorReverseConverter;
        this.userRepository = userRepository;
    }

    @Override
    public Mono<Book> convert(BookData source) {
        Mono<BookStoreUser> authorMono = userRepository.findById(source.getAuthorEmail())
                .switchIfEmpty(createAuthorModel(source).flatMap(userRepository::save));
        Mono<Book.BookBuilder> bookBuilderMono = Mono.just(Book.builder().title(source.getTitle())
                .description(source.getDescription())
                .publishingDate(source.getPublishingDate())
                .price(source.getPrice()));
        return Mono.zip(bookBuilderMono, authorMono, this::setAuthorToBook);
    }

    private Book setAuthorToBook(Book.BookBuilder book, BookStoreUser author) {
        book.authorEmail(author.getEmail());
        return book.build();
    }

    private Mono<BookStoreUser> createAuthorModel(BookData source) {
        AuthorData data = new AuthorData();
        data.setEmail(source.getAuthorEmail());
        data.setName(source.getAuthorName());
        return authorReverseConverter.convert(data);
    }
}
