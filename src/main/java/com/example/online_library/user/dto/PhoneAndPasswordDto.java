package com.example.online_library.user.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PhoneAndPasswordDto {
    private String phoneNumber;
    private String password;
}
