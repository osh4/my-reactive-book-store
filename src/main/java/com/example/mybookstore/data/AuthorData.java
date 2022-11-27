package com.example.mybookstore.data;

import lombok.*;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class AuthorData {
    private String email;
    private String name;
    private LocalDate birthDate;
    private Set<BookData> books;
}
