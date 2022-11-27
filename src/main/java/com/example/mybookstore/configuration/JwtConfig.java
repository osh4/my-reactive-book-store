package com.example.mybookstore.configuration;

import lombok.Getter;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
@ToString
public class JwtConfig {
    @Value("${jwt.auth.uri:/login}")
    private String authUri;
    @Value("${jwt.auth.uri:/logout}")
    private String revokeUri;
    @Value("${jwt.auth.header:Authorization}")
    private String header;
    @Value("${jwt.auth.prefix:Bearer}")
    private String prefix;
    @Value("${jwt.auth.expiration:300}")
    private int expiration;
    @Value("${jwt.auth.secret:JwtSecretKey}")
    private String secret;
}
