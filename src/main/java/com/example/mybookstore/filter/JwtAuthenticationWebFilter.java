package com.example.mybookstore.filter;

import com.example.mybookstore.configuration.JwtConfig;
import com.example.mybookstore.security.JwtAuthenticationConverter;
import com.example.mybookstore.security.JwtServerAuthenticationSuccessHandler;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.web.server.authentication.AuthenticationWebFilter;
import org.springframework.security.web.server.util.matcher.PathPatternParserServerWebExchangeMatcher;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationWebFilter extends AuthenticationWebFilter {

    public JwtAuthenticationWebFilter(ReactiveAuthenticationManager authenticationManager, JwtConfig jwtConfig,
                                      JwtServerAuthenticationSuccessHandler reactiveAuthenticationSuccessHandler,
                                      JwtAuthenticationConverter converter) {
        super(authenticationManager);
        setServerAuthenticationConverter(converter);
        setAuthenticationSuccessHandler(reactiveAuthenticationSuccessHandler);
        PathPatternParserServerWebExchangeMatcher pathMatcher =
                new PathPatternParserServerWebExchangeMatcher(jwtConfig.getAuthUri(), HttpMethod.POST);
        setRequiresAuthenticationMatcher(pathMatcher);
    }
}
