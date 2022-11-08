package com.example.mybookstore.repository;

import com.example.mybookstore.model.BookStoreUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<BookStoreUser, String> {

}
