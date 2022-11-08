package com.example.mybookstore.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookData {

    private String title;
    private String description;
    private LocalDate publishingDate;
    private BigDecimal price;
    private String authorName;
    private String authorEmail;
}
