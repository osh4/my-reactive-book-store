package com.example.mybookstore.filter;

import com.example.mybookstore.configuration.JwtConfig;
import com.example.mybookstore.service.JwtTokenService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.apache.commons.lang3.BooleanUtils.isFalse;


@Component
@Slf4j
@AllArgsConstructor
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final JwtTokenService jwtTokenService;
    private final JwtConfig jwtConfig;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        String header = request.getHeader(jwtConfig.getHeader());
        String jwtPrefix = jwtConfig.getPrefix();
        if (StringUtils.isBlank(header) || isFalse(header.startsWith(jwtPrefix))) {
            filterChain.doFilter(request, response);
            return;
        }
        String token = header.replace(jwtPrefix + StringUtils.SPACE, StringUtils.EMPTY);
        try {
            String user = jwtTokenService.getSubject(token);
            if (StringUtils.isBlank(user)) {
                log.warn("Authentication failed, user is empty");
                SecurityContextHolder.clearContext();
                filterChain.doFilter(request, response);
                return;
            }
            var authorities = jwtTokenService.getAuthorities(token);
            var authToken = new UsernamePasswordAuthenticationToken(user, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authToken);

        } catch (Exception ex) {
            log.error("Auth error", ex);
            SecurityContextHolder.clearContext();
        }
        filterChain.doFilter(request, response);
    }
}