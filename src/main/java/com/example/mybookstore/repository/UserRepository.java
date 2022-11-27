package com.example.mybookstore.repository;

import com.example.mybookstore.model.BookStoreUser;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;

public interface UserRepository extends ReactiveCrudRepository<BookStoreUser, String> {
}
