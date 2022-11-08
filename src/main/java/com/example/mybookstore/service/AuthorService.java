package com.example.mybookstore.service;

import com.example.mybookstore.data.AuthorData;

import java.util.List;

public interface AuthorService {
    List<AuthorData> getAllAuthors();

    void addAuthor(AuthorData author);

    void removeAuthor(AuthorData author, boolean removeBooks);
}
