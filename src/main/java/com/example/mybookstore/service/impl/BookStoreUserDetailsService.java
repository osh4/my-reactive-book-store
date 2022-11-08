package com.example.mybookstore.service.impl;

import com.example.mybookstore.model.BookStoreUser;
import com.example.mybookstore.repository.UserRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Qualifier("userDetailsService")
public class BookStoreUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public BookStoreUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException("Username should not be blank");
        }
        Optional<BookStoreUser> userOptional = userRepository.findById(username);
        if (userOptional.isEmpty()) {
            throw new UsernameNotFoundException("Username not found");
        }
        return userOptional.get();
    }
}
