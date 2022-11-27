package com.example.mybookstore.service.impl;


import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.JWTVerifier;
import com.example.mybookstore.configuration.JwtConfig;
import com.example.mybookstore.model.BookStoreUser;
import com.example.mybookstore.service.JwtTokenService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
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

    private static final long THRESHOLD = 10;
    private final JwtConfig jwtConfig;

    @Override
    public String generateJwtToken(BookStoreUser userPrincipal) {
        String[] claims = getClaimsFromUser(userPrincipal);
        return JWT.create()
                .withIssuedAt(new Date()).withSubject(userPrincipal.getUsername())
                .withArrayClaim(AUTHORITIES, claims)
                .withExpiresAt(getExpiresAt())
                .sign(HMAC512(jwtConfig.getSecret().getBytes()));
    }

    @Override
    public String refreshToken(BookStoreUser userPrincipal, String token) {
        String userName = getSubject(token);
        if (!StringUtils.equals(userPrincipal.getEmail(), userName)) {
            throw new JWTVerificationException("Incorrect user");
        }
        String[] claims = getClaimsFromUser(userPrincipal);
        return JWT.create()
                .withIssuedAt(new Date()).withSubject(userPrincipal.getUsername())
                .withArrayClaim(AUTHORITIES, claims)
                .withExpiresAt(getExpiresAt())
                .sign(HMAC512(jwtConfig.getSecret().getBytes()));
    }

    @Override
    public boolean needRefresh(String token) {
        Date expirationDate = getExpirationDate(token);
        return LocalDateTime.now().plusSeconds(THRESHOLD).isBefore(LocalDateTime.from(expirationDate.toInstant()));
    }

    private Instant getExpiresAt() {
        return LocalDateTime.now().plus(jwtConfig.getExpiration(), ChronoUnit.SECONDS)
                .toInstant(ZoneOffset.MIN);
    }

    @Override
    public List<GrantedAuthority> getAuthorities(String token) {
        return stream(getVerifiedToken(token).getClaim(AUTHORITIES).asArray(String.class))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
    }

    @Override
    public String getSubject(String token) {
        return getVerifiedToken(token).getSubject();
    }

    @Override
    public Date getExpirationDate(String token) {
        return getVerifiedToken(token).getExpiresAt();
    }

    private DecodedJWT getVerifiedToken(String token) {
        return getJWTVerifier().verify(token);
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