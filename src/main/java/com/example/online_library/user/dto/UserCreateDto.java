package com.example.online_library.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserCreateDto {
    private String name;
    private String surname;
    private String email;
    private String phoneNumber;
    private String password;
    private MultipartFile picture;
    private String role;
}
