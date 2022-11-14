package com.example.mybookstore.service.impl;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.mybookstore.configuration.JwtConfig;
import com.example.mybookstore.model.BookStoreUser;
import com.example.mybookstore.model.Role;
import com.example.mybookstore.service.JwtTokenService;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.example.mybookstore.configuration.Constants.AUTHORITIES;
import static com.example.mybookstore.configuration.Constants.TOKEN_CANNOT_BE_VERIFIED;
import static java.util.Arrays.stream;

@Component
@AllArgsConstructor
public class JwtTokenServiceImpl implements JwtTokenService {

    private final JwtConfig jwtConfig;

    @Override
    public String generateJwtToken(BookStoreUser userPrincipal) {
        String[] claims = getClaimsFromUser(userPrincipal);
        return JWT.create()
                .withIssuedAt(new Date()).withSubject(userPrincipal.getUsername())
                .withArrayClaim(AUTHORITIES, claims).withExpiresAt(new Date(System.currentTimeMillis() + jwtConfig.getExpiration()))
                .sign(HMAC512(jwtConfig.getSecret().getBytes()));
    }

    @Override
    public List<GrantedAuthority> getAuthorities(String token) {
        JWTVerifier verifier = getJWTVerifier();
        return stream(verifier.verify(token).getClaim(AUTHORITIES).asArray(String.class))
                .map(Role::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getSubject(String token) {
        JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getSubject();
    }

    private JWTVerifier getJWTVerifier() {
        try {
            Algorithm algorithm = HMAC512(jwtConfig.getSecret());
            return JWT.require(algorithm).build();
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException(TOKEN_CANNOT_BE_VERIFIED);
        }
    }

    private String[] getClaimsFromUser(BookStoreUser user) {
        List<String> authorities = new ArrayList<>();
        for (GrantedAuthority grantedAuthority : user.getAuthorities()) {
            authorities.add(grantedAuthority.getAuthority());
        }
        return authorities.toArray(new String[0]);
    }
}