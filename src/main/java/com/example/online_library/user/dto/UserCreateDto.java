package com.example.online_library.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCreateDto {
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String password;
    private String role;
}
