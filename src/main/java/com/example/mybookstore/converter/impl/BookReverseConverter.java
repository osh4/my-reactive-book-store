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
        Mono<Book> bookMono = Mono.just(new Book()).map(book -> populateFields(book, source));
        return Mono.zip(bookMono, authorMono, this::setAuthorToBook);
    }

    private Book setAuthorToBook(Book book, BookStoreUser author) {
        book.setAuthorEmail(author.getEmail());
        return book;
    }

    private Book populateFields(Book book, BookData source) {
        book.setTitle(source.getTitle());
        book.setDescription(source.getDescription());
        book.setPublishingDate(source.getPublishingDate());
        book.setPrice(source.getPrice());
        return book;
    }

    private Mono<BookStoreUser> createAuthorModel(BookData source) {
        AuthorData data = new AuthorData();
        data.setEmail(source.getAuthorEmail());
        data.setName(source.getAuthorName());
        return authorReverseConverter.convert(data);
    }
}
