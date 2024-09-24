package com.example.hexFoodieBack.controller;

import com.example.hexFoodieBack.entity.User;
import com.example.hexFoodieBack.exception.UserException;
import com.example.hexFoodieBack.exception.ValidationException;
import com.example.hexFoodieBack.repository.UserRepository;
import com.example.hexFoodieBack.request.EmailRequest;
import com.example.hexFoodieBack.request.LoginRequest;
import com.example.hexFoodieBack.request.SignupRequest;
import com.example.hexFoodieBack.response.LoginResponse;
import com.example.hexFoodieBack.response.SignupResponse;
import com.example.hexFoodieBack.service.UserRegisterServiceImple;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/auth")
public class UserController {
    @Autowired
    UserRegisterServiceImple userRegisterServiceImple;

    @Autowired
    UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponse> signup(@RequestBody SignupRequest signupRequest) throws ValidationException {
        return new ResponseEntity<>(userRegisterServiceImple.signup(signupRequest), HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) throws ValidationException, UserException {
        return new ResponseEntity<>(userRegisterServiceImple.login(loginRequest),HttpStatus.OK);
    }
    @PostMapping("/getuser")
    public ResponseEntity<User> userData(@RequestBody EmailRequest emailRequest){
        return userRegisterServiceImple.userData(emailRequest);
    }
}
