package com.example.mybookstore.controller;

import com.example.mybookstore.data.BookData;
import com.example.mybookstore.service.BookService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/book")
@Slf4j
@AllArgsConstructor
public class BookController {

    private final BookService bookService;

    @GetMapping
    public Flux<BookData> getBooks() {
        return bookService.getAllBooks();
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_AUTHOR')")
    @PostMapping
    public Mono<ResponseEntity<String>> addBook(@RequestBody BookData bookData) {
        return bookService.addBook(bookData)
                .flatMap(s -> Mono.just(ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON).body(s)))
                .onErrorReturn(ResponseEntity.unprocessableEntity().body("Can't add the book"));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_AUTHOR')")
    @DeleteMapping
    public Mono<ResponseEntity<String>> removeBook(@RequestBody BookData bookData) {
        return bookService.removeBook(bookData)
                .flatMap(s -> Mono.just(ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON).body(s)))
                .onErrorReturn(ResponseEntity.unprocessableEntity().body("Can't remove the book"));
    }

}
