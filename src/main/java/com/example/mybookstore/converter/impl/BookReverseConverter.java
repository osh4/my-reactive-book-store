package com.example.mybookstore.converter.impl;

import com.example.mybookstore.converter.Converter;
import com.example.mybookstore.data.AuthorData;
import com.example.mybookstore.data.BookData;
import com.example.mybookstore.model.Author;
import com.example.mybookstore.model.Book;
import com.example.mybookstore.repository.AuthorRepository;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BookReverseConverter implements Converter<BookData, Book> {
    private final Converter<AuthorData, Author> authorReverseConverter;
    private final AuthorRepository authorRepository;

    public BookReverseConverter(Converter<AuthorData, Author> authorReverseConverter, AuthorRepository authorRepository) {
        this.authorReverseConverter = authorReverseConverter;
        this.authorRepository = authorRepository;
    }

    @Override
    public Book convert(BookData source) {
        Book book = new Book();
        book.setTitle(source.getTitle());
        book.setDescription(source.getDescription());
        book.setPublishingDate(source.getPublishingDate());
        book.setPrice(source.getPrice());
        Optional<Author> authorOptional = authorRepository.findById(source.getAuthorEmail());
        Author author = null;
        if (authorOptional.isEmpty()) {
            AuthorData data = new AuthorData();
            data.setEmail(source.getAuthorEmail());
            data.setName(source.getAuthorName());
            author = authorReverseConverter.convert(data);
            authorRepository.save(author);
        }
        book.setAuthor(authorOptional.orElse(author));
        return book;
    }
}
