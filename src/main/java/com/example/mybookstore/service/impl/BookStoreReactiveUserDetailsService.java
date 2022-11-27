package com.example.mybookstore.service.impl;

import com.example.mybookstore.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
@Qualifier("userDetailsService")
public class BookStoreReactiveUserDetailsService implements ReactiveUserDetailsService {
    private final UserRepository userRepository;

    public BookStoreReactiveUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Mono<UserDetails> findByUsername(String username) {
        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("Username should not be blank");
        }
        return userRepository.findById(username)
                .map(UserDetails.class::cast)
                .switchIfEmpty(Mono.error(new UsernameNotFoundException("Username not found")));
    }
}
