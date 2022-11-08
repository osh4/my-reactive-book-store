package com.example.mybookstore.controller;

import com.example.mybookstore.data.BookData;
import com.example.mybookstore.service.BookService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/book")
@Slf4j
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    private ResponseEntity<List<BookData>> getBooks() {
        final List<BookData> books = bookService.getAllBooks();
        return ResponseEntity.ok(books);
    }

    @Secured({"ROLE_ADMIN", "ROLE_AUTHOR"})
    @PostMapping
    private ResponseEntity<?> addBook(@RequestBody BookData bookData) {
        try {
            bookService.addBook(bookData);
            return ResponseEntity.ok(bookData);
        } catch (Exception ex) {
            log.warn(ex.getMessage(), ex);
            return ResponseEntity.unprocessableEntity().body("Some issues while adding the book");
        }
    }

    @Secured({"ROLE_ADMIN", "ROLE_AUTHOR"})
    @DeleteMapping
    private ResponseEntity<?> removeBook(@RequestBody BookData bookData) {
        try {
            bookService.removeBook(bookData);
            return ResponseEntity.ok("The book was removed");
        } catch (Exception ex) {
            log.warn(ex.getMessage(), ex);
            return ResponseEntity.unprocessableEntity().body("Some issues while removing the book");
        }
    }

}
