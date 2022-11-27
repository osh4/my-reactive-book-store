package com.example.mybookstore.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.security.core.GrantedAuthority;

import java.util.List;

import static java.util.Objects.nonNull;

@Data
@Table("roles")
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Role implements GrantedAuthority, Persistable<String> {
    @Id
    private String name;
    private List<String> userEmails;

    @Override
    public String getAuthority() {
        return name;
    }

    @Override
    public String getId() {
        return name;
    }

    @Override
    public boolean isNew() {
        return nonNull(name);
    }
}
