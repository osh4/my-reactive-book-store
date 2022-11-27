package com.example.mybookstore.security;

import com.example.mybookstore.configuration.JwtConfig;
import com.example.mybookstore.model.BookStoreUser;
import com.example.mybookstore.service.JwtTokenService;
import com.example.mybookstore.service.impl.BookStoreReactiveUserDetailsService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.WebFilterExchange;
import org.springframework.security.web.server.authentication.ServerAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

@Component
public class JwtServerAuthenticationSuccessHandler implements ServerAuthenticationSuccessHandler {
    private final JwtConfig jwtConfig;
    private final JwtTokenService jwtTokenService;
    private final BookStoreReactiveUserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;

    public JwtServerAuthenticationSuccessHandler(JwtConfig jwtConfig, JwtTokenService jwtTokenService,
                                                 BookStoreReactiveUserDetailsService userDetailsService) {
        this.jwtConfig = jwtConfig;
        this.jwtTokenService = jwtTokenService;
        this.userDetailsService = userDetailsService;
        this.objectMapper = new ObjectMapper();
    }

    @SneakyThrows
    @Override
    public Mono<Void> onAuthenticationSuccess(WebFilterExchange webFilterExchange, Authentication authentication) {
        ServerHttpResponse response = webFilterExchange.getExchange().getResponse();
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        Mono<DataBuffer> buffer = userDetailsService.findByUsername(authentication.getName())
                .filter(BookStoreUser.class::isInstance)
                .map(BookStoreUser.class::cast)
                .map(jwtTokenService::generateJwtToken)
                .map(this::tokenToMap)
                .map(this::mapToJson)
                .map(String::getBytes)
                .map(response.bufferFactory()::wrap);
        return response.writeWith(buffer);
    }

    private String mapToJson(Map<String, String> headersMap) {
        try {
            return objectMapper.writeValueAsString(headersMap);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private Map<String, String> tokenToMap(String token) {
        var map = new HashMap<String, String>();
        map.put(jwtConfig.getHeader(), jwtConfig.getPrefix() + " " + token);
        return map;
    }
}
