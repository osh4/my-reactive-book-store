package com.example.mybookstore.filter;

import com.example.mybookstore.configuration.JwtConfig;
import com.example.mybookstore.service.JwtTokenService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import reactor.core.publisher.Mono;

import java.util.Optional;

import static org.apache.commons.lang3.BooleanUtils.isFalse;

@Component
@Slf4j
public class JwtAuthorizationWebFilter implements WebFilter {

    private final JwtTokenService jwtTokenService;
    private final JwtConfig jwtConfig;

    public JwtAuthorizationWebFilter(JwtTokenService jwtTokenService, JwtConfig jwtConfig) {
        this.jwtTokenService = jwtTokenService;
        this.jwtConfig = jwtConfig;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
        Optional<String> jwtHeaderOptional = Optional.ofNullable(exchange.getRequest().getHeaders()
                .get(jwtConfig.getHeader())).map(x -> StringUtils.trim(x.get(0)));
        String jwtPrefix = jwtConfig.getPrefix();
        if (jwtHeaderOptional.isEmpty() || isFalse(jwtHeaderOptional.get().startsWith(jwtPrefix))) {
            return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.clearContext());
        }
        String token = jwtHeaderOptional.get().replace(jwtPrefix + StringUtils.SPACE, StringUtils.EMPTY);
        try {
            String user = jwtTokenService.getSubject(token);
            if (StringUtils.isBlank(user)) {
                log.warn("Authentication failed, user is empty");
                return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.clearContext());
            }
            var authorities = jwtTokenService.getAuthorities(token);
            var authToken = new UsernamePasswordAuthenticationToken(user, null, authorities);
            return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.withAuthentication(authToken));
        } catch (Exception ex) {
            log.error("Auth error", ex);
            return chain.filter(exchange).contextWrite(ReactiveSecurityContextHolder.clearContext());
        }
    }
}