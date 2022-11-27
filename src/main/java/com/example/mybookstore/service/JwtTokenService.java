package com.example.mybookstore.service;

import com.example.mybookstore.model.BookStoreUser;
import org.springframework.security.core.GrantedAuthority;

import java.util.Date;
import java.util.List;

public interface JwtTokenService {
    String generateJwtToken(BookStoreUser userPrincipal);
    List<GrantedAuthority> getAuthorities(String token);
    String getSubject(String token);
    Date getExpirationDate(String token);
    String refreshToken(BookStoreUser userPrincipal, String token);
    boolean needRefresh(String token);
}
