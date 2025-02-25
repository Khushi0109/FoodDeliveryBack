package com.example.hexFoodieBack.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String jwt;
    private String message;
    private Boolean success;
    private String email;
    private String role;
}
