package com.example.mybookstore.controller;

import com.example.mybookstore.data.UserData;
import com.example.mybookstore.model.BookStoreUser;
import com.example.mybookstore.security.JWTTokenProvider;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
public class AuthController {
    private final UserDetailsService userDetailsService;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;

    public AuthController(UserDetailsService userDetailsService, AuthenticationManager authenticationManager,
                          JWTTokenProvider jwtTokenProvider) {
        this.userDetailsService = userDetailsService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UserData user) {
        authenticate(user.getEmail(), user.getPassword());
        BookStoreUser loginUser = (BookStoreUser) userDetailsService.loadUserByUsername(user.getEmail());
        return ResponseEntity.ok().body(jwtTokenProvider.generateJwtToken(loginUser));
    }

    private void authenticate(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
    }
}
