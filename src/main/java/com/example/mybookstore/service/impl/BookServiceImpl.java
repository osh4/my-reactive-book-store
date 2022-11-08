package com.example.mybookstore.service.impl;

import com.example.mybookstore.converter.impl.BookConverter;
import com.example.mybookstore.converter.impl.BookReverseConverter;
import com.example.mybookstore.data.BookData;
import com.example.mybookstore.model.Book;
import com.example.mybookstore.repository.BookRepository;
import com.example.mybookstore.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookConverter bookConverter;
    private final BookReverseConverter bookReverseConverter;

    @Override
    public List<BookData> getAllBooks() {
        return bookConverter.convertAll(bookRepository.findAll());
    }

    @Override
    public void addBook(BookData bookData) {
        if (bookRepository.findById(bookData.getTitle()).isPresent()) {
            throw new DuplicateKeyException("The book already exists");
        }
        bookRepository.save(bookReverseConverter.convert(bookData));
    }

    @Override
    public void removeBook(BookData bookData) {
        Book book = bookRepository.findById(bookData.getTitle())
                .orElseThrow(() -> new EntityNotFoundException("There is no such a book"));
        bookRepository.delete(book);
    }
}
