package com.example.hexFoodieBack.service;

import com.example.hexFoodieBack.exception.ValidationException;

public interface ValidationService {
    public Boolean nameValidation(String name) throws ValidationException;
    public Boolean emailValidation(String email) throws ValidationException;

    public Boolean mobileNumber(String number)throws ValidationException;
}
