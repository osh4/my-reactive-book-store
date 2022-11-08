package com.example.mybookstore.repository;

import com.example.mybookstore.model.Author;
import com.example.mybookstore.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, String> {

}
