package com.example.mybookstore.repository;

import com.example.mybookstore.model.BookStoreUser;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

public interface UserRepository extends ReactiveCrudRepository<BookStoreUser, String> {
    @Query("SELECT * FROM book_store_user WHERE LOWER(email) = LOWER(:username)")
    Mono<BookStoreUser> findByUsername(String username);
}
