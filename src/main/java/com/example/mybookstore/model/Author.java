package com.example.mybookstore.model;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Getter
@Setter
public class Author extends BookStoreUser {

    @Column
    private LocalDate birthDate;
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL)
    private Set<Book> books;

}
