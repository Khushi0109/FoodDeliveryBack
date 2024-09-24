package com.example.hexFoodieBack.request;

import lombok.Getter;

@Getter
public class SignupRequest {
    private String email;
    private  String mobileNumber;
    private String name;
    private String password;
    private String role;
}
