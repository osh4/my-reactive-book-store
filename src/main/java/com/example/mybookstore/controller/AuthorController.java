package com.example.mybookstore.controller;

import com.example.mybookstore.data.AuthorData;
import com.example.mybookstore.service.AuthorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@AllArgsConstructor
public class AuthorController {

    private final AuthorService authorService;


    @GetMapping("/author")
    private ResponseEntity<List<AuthorData>> getAuthors() {
        final List<AuthorData> authors = authorService.getAllAuthors();
        return ResponseEntity.ok(authors);
    }

    @Secured({"ROLE_ADMIN"})
    @PostMapping("/author")
    private ResponseEntity<?> addAuthor(@RequestBody AuthorData authorData) {
        try {
            authorService.addAuthor(authorData);
            return ResponseEntity.ok(authorData);
        } catch (Exception ex) {
            log.warn(ex.getMessage(), ex);
            return ResponseEntity.unprocessableEntity().body("Some issues while adding the author");
        }
    }

    @Secured({"ROLE_ADMIN"})
    @DeleteMapping("/author")
    private ResponseEntity<?> removeAuthor(@RequestBody AuthorData authorData,
                                           @RequestParam(required = false, defaultValue = "false")
                                           boolean removeBooks) {
        try {
            authorService.removeAuthor(authorData, removeBooks);
            return ResponseEntity.ok("The book was removed");
        } catch (Exception ex) {
            log.warn(ex.getMessage(), ex);
            return ResponseEntity.unprocessableEntity().body("Some issues while removing the book");
        }
    }
}
