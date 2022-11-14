package com.example.mybookstore.filter;

import com.example.mybookstore.configuration.JwtConfig;
import com.example.mybookstore.data.UserData;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.example.mybookstore.configuration.Constants.MSG_INVALID_CREDENTIALS;

@Component("usernamePasswordAuthenticationFilter")
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final ObjectMapper objectMapper;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JwtConfig jwtConfig,
                                   AuthenticationSuccessHandler authenticationSuccessHandler) {
        super(authenticationManager);
        this.setRequiresAuthenticationRequestMatcher(
                new AntPathRequestMatcher(jwtConfig.getAuthUri(), HttpMethod.POST.name()));
        this.objectMapper = new ObjectMapper();
        this.setAuthenticationSuccessHandler(authenticationSuccessHandler);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        try {
            UserData userData = objectMapper.readValue(request.getInputStream(), UserData.class);
            var authToken = new UsernamePasswordAuthenticationToken(userData.getEmail(), userData.getPassword());
            return getAuthenticationManager().authenticate(authToken);
        } catch (AuthenticationException ex) {
            log.error(MSG_INVALID_CREDENTIALS);
            throw new InternalAuthenticationServiceException(MSG_INVALID_CREDENTIALS);
        } catch (IOException ex) {
            log.error("Something went wrong", ex);
            throw new RuntimeException();
        }
    }
}
