package com.example.mybookstore.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AuthorData {
    private String email;
    private String name;
    private LocalDate birthDate;
    private Set<BookData> books;
}
