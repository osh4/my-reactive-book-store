package com.example.mybookstore.security;

import com.example.mybookstore.data.UserData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Component
@Slf4j
public class JwtAuthenticationConverter implements ServerAuthenticationConverter {

    private final ObjectMapper objectMapper;

    public JwtAuthenticationConverter() {
        objectMapper = new ObjectMapper();
    }

    @SneakyThrows
    @Override
    public Mono<Authentication> convert(ServerWebExchange exchange) {
        return Mono.from(exchange.getRequest().getBody().map(DataBuffer::asInputStream).map(dataBuffer -> {
                    try {
                        return objectMapper.readValue(dataBuffer, UserData.class);
                    } catch (IOException e) {
                        return UserData.EMPTY_USER;
                    }
                })
                .map(x -> new UsernamePasswordAuthenticationToken(x.getEmail(), x.getPassword()))
        );
    }
}
