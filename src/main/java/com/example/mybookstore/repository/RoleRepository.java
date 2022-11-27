package com.example.mybookstore.repository;

import com.example.mybookstore.model.Role;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import reactor.core.publisher.Flux;


public interface RoleRepository extends R2dbcRepository<Role, String> {
    Flux<Role> findAllByUserEmailsContaining(String userEmail);
}
