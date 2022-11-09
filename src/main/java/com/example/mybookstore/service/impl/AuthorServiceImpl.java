package com.example.mybookstore.service.impl;

import com.example.mybookstore.converter.impl.AuthorConverter;
import com.example.mybookstore.converter.impl.AuthorReverseConverter;
import com.example.mybookstore.data.AuthorData;
import com.example.mybookstore.model.Author;
import com.example.mybookstore.repository.AuthorRepository;
import com.example.mybookstore.repository.BookRepository;
import com.example.mybookstore.service.AuthorService;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Component;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Component
public class AuthorServiceImpl implements AuthorService {

    private final AuthorReverseConverter authorReverseConverter;
    private final AuthorConverter authorConverter;
    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorServiceImpl(AuthorReverseConverter authorReverseConverter, AuthorConverter authorConverter,
                             AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorReverseConverter = authorReverseConverter;
        this.authorConverter = authorConverter;
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    @Override
    public List<AuthorData> getAllAuthors() {
        return authorConverter.convertAll(authorRepository.findAll());
    }

    @Override
    public void addAuthor(AuthorData authorData) {
        if (authorRepository.findById(authorData.getEmail()).isPresent()) {
            throw new DuplicateKeyException("The author already exists");
        }

        authorRepository.save(authorReverseConverter.convert(authorData));
    }

    @Override
    public void removeAuthor(AuthorData authorData, boolean shouldRemoveBooks) {
        Optional<Author> authorOptional = authorRepository.findById(authorData.getEmail());
        if (authorOptional.isEmpty()) {
            throw new EntityNotFoundException("There is no such an author");
        }
        Author author = authorOptional.get();
        if (CollectionUtils.isNotEmpty(author.getBooks()) && !shouldRemoveBooks) {
            throw new EntityExistsException("The author has books, please delete all books before deleting the author");
        }
        if (CollectionUtils.isNotEmpty(author.getBooks())){
            bookRepository.deleteAll(author.getBooks());
        }
        authorRepository.delete(author);
    }
}
