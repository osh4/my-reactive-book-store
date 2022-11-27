package com.example.mybookstore.data;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class BookData {

    private String title;
    private String description;
    private LocalDate publishingDate;
    private BigDecimal price;
    private String authorName;
    private String authorEmail;
}
