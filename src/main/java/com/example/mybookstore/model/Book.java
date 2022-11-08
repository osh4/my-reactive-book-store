package com.example.mybookstore.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@Setter
public class Book {
    @Id
    @Column(nullable = false, unique = true)
    private String title;

    @Column(nullable = false)
    private String description;
    @Column(nullable = false)
    private LocalDate publishingDate;
    @Column(nullable = false, scale = 2, precision = 10)
    private BigDecimal price;
    @ManyToOne
    @JoinColumn(referencedColumnName = "email", nullable = false)
    private Author author;
}
