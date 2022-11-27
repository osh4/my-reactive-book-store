package com.example.mybookstore.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Persistable;
import org.springframework.data.relational.core.mapping.Table;

import java.math.BigDecimal;
import java.time.LocalDate;

import static java.util.Objects.nonNull;

@Data
@Table("books")
@Builder(toBuilder = true)
public class Book implements Persistable<String> {
    @Id
    private String title;
    private String description;
    private LocalDate publishingDate;
    private BigDecimal price;
    private String authorEmail;

    @Override
    public String getId() {
        return title;
    }

    @Override
    public boolean isNew() {
        return nonNull(title);
    }
}
