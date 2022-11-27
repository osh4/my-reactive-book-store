package com.example.mybookstore.service.impl;

import com.example.mybookstore.converter.Converter;
import com.example.mybookstore.converter.impl.BookConverter;
import com.example.mybookstore.data.BookData;
import com.example.mybookstore.exception.EntityNotFoundException;
import com.example.mybookstore.model.Book;
import com.example.mybookstore.repository.BookRepository;
import com.example.mybookstore.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookConverter bookConverter;
    private final Converter<BookData, Mono<Book>> bookReverseConverter;

    @Override
    public Flux<BookData> getAllBooks() {
        return bookConverter.convertAll(bookRepository.findAll());
    }

    @Override
    public Mono<String> addBook(BookData bookData) {
        return bookRepository.findById(bookData.getTitle())
                .flatMap(x -> Mono.error(new DuplicateKeyException("The book already exists")))
                .switchIfEmpty(bookReverseConverter.convert(bookData).flatMap(bookRepository::save))
                .flatMap(x -> Mono.just("The book successfully added"));
    }

    @Override
    public Mono<String> removeBook(BookData bookData) {
        return bookRepository.findById(bookData.getTitle())
                .flatMap(bookRepository::delete)
                .flatMap(x -> Mono.just("The book was successfully removed"))
                .switchIfEmpty(Mono.error(new EntityNotFoundException("There is no such a book")));
    }
}
