package com.example.mybookstore.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserData {
    public static final UserData EMPTY_USER = new UserData();
    private String email;
    private String password;
}
