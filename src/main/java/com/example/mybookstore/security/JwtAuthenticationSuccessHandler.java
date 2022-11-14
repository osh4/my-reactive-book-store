package com.example.mybookstore.security;

import com.example.mybookstore.configuration.JwtConfig;
import com.example.mybookstore.model.BookStoreUser;
import com.example.mybookstore.service.JwtTokenService;
import com.example.mybookstore.service.impl.BookStoreUserDetailsService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

@Component("authenticationSuccessHandler")
public class JwtAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private final JwtConfig jwtConfig;
    private final JwtTokenService jwtTokenService;
    private final BookStoreUserDetailsService userDetailsService;
    private final ObjectMapper objectMapper;

    public JwtAuthenticationSuccessHandler(JwtConfig jwtConfig, JwtTokenService jwtTokenService,
                                           BookStoreUserDetailsService userDetailsService) {
        this.jwtConfig = jwtConfig;
        this.jwtTokenService = jwtTokenService;
        this.userDetailsService = userDetailsService;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        String userName = authentication.getName();
        BookStoreUser userDetails = (BookStoreUser) userDetailsService.loadUserByUsername(userName);
        String token = jwtTokenService.generateJwtToken(userDetails);
        var map = new HashMap<String, String>();
        map.put(jwtConfig.getHeader(), jwtConfig.getPrefix() + " " + token);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.getWriter().write(objectMapper.writeValueAsString(map));
    }
}
