package com.example.mybookstore.controller;

import com.example.mybookstore.data.AuthorData;
import com.example.mybookstore.service.AuthorService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@Slf4j
@AllArgsConstructor
public class AuthorController {

    private final AuthorService authorService;

    @GetMapping("/author")
    public Flux<AuthorData> getAuthors() {
        return authorService.getAllAuthors();
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/author")
    public Mono<ResponseEntity<String>> addAuthor(@RequestBody AuthorData authorData) {
        return authorService.addAuthor(authorData)
                .flatMap(s -> Mono.just(ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON).body(s)))
                .onErrorReturn(ResponseEntity.unprocessableEntity().body("Can't add the author"));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/author")
    private Mono<ResponseEntity<String>> removeAuthor(@RequestBody AuthorData authorData,
                                                      @RequestParam(required = false, defaultValue = "false")
                                                      boolean removeBooks) {
        return authorService.removeAuthor(authorData, removeBooks)
                .flatMap(s -> Mono.just(ResponseEntity.accepted().contentType(MediaType.APPLICATION_JSON).body(s)))
                .onErrorReturn(ResponseEntity.unprocessableEntity().body("Can't remove the author"));
    }
}
