package com.example.hexFoodieBack.service;

import com.example.hexFoodieBack.entity.LoginAttempt;
import com.example.hexFoodieBack.entity.User;
import com.example.hexFoodieBack.exception.UserException;
import com.example.hexFoodieBack.exception.ValidationException;
import com.example.hexFoodieBack.repository.LoginAttemptRepository;
import com.example.hexFoodieBack.repository.UserRepository;
import com.example.hexFoodieBack.request.EmailRequest;
import com.example.hexFoodieBack.request.LoginRequest;
import com.example.hexFoodieBack.request.SignupRequest;
import com.example.hexFoodieBack.response.LoginResponse;
import com.example.hexFoodieBack.response.SignupResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;

@Service
@Slf4j
public class UserRegisterServiceImple implements UserRegisterService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtService jwtService;

    @Autowired
    LoginAttemptRepository loginAttemptRepository;

    @Autowired
    private ValidationServiceImple validationServiceImple;

    private Long continiousAttempDuration=60*60*1000L;

    private Long lockDuration=5*60*1000L;
    @Override
    public SignupResponse signup(SignupRequest signupRequest) throws ValidationException {
        try{
//            if(validationServiceImple.nameValidation(signupRequest.getName())){
//                log.info("Provided first name is not correct");
//                SignupResponse signupResponse=new SignupResponse();
//                signupResponse.setMessage("Provided first name is not correct");
//                signupResponse.setSuccess(false);
//                return signupResponse;
//            }
//            if(validationServiceImple.mobileNumber(signupRequest.getMobileNumber())){
//                log.info("Provided Contact Number is not correct");
//                SignupResponse signupResponse = new SignupResponse();
//                signupResponse.setMessage("Provided Contact Number is not correct");
//                signupResponse.setSuccess(false);
//                return signupResponse;
//            }
//            if(validationServiceImple.emailValidation(signupRequest.getEmail())){
//                log.info("Provided email is not correct");
//                SignupResponse signupResponse=new SignupResponse();
//                signupResponse.setMessage("Provided email is not correct");
//                signupResponse.setSuccess(false);
//                return signupResponse;
//            }
            User user1 = userRepository.findByEmail(signupRequest.getEmail().toLowerCase());
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            if (user1 == null) {
                User u = new User();
                u.setName(signupRequest.getName());
                u.setEmail(signupRequest.getEmail().toLowerCase());
                u.setPassword(bCryptPasswordEncoder.encode(signupRequest.getPassword()));
                u.setMobileNumber(signupRequest.getMobileNumber());
                u.setRole(signupRequest.getRole());
                userRepository.save(u);
                SignupResponse signupResponse=new SignupResponse();
                signupResponse.setMessage("User Registered Successfully");
                signupResponse.setSuccess(true);
                log.info("User Registered successfully");
                return signupResponse;
            } else {
                SignupResponse signupResponse=new SignupResponse();
                signupResponse.setMessage("User Already Present");
                signupResponse.setSuccess(false);
                log.info("User Already Present");
                return signupResponse;
            }
        }catch (Exception e){
            log.error("Error in creating account",e);
            SignupResponse signupResponse=new SignupResponse();
            signupResponse.setMessage("Some error occured in creating account");
            signupResponse.setSuccess(false);
            return signupResponse;
        }
    }

    @Override
    public LoginResponse login(LoginRequest loginRequest) throws UserException {
        User user = userRepository.findByEmail(loginRequest.getEmail().toLowerCase());
        Date currentDate= Calendar.getInstance().getTime();
        try {
            if(!validationServiceImple.emailValidation(loginRequest.getEmail())) {
                LoginResponse loginResponse=new LoginResponse();
                loginResponse.setMessage("Email must be like test@example.com");
                loginResponse.setJwt(null);
                loginResponse.setEmail(null);
                loginResponse.setSuccess(false);
                log.info("Email must be like test@example.com");
                return loginResponse;
            }

            if (user == null) {
                LoginResponse loginResponse=new LoginResponse();
                loginResponse.setMessage("Email doesn't exist");
                loginResponse.setJwt(null);
                loginResponse.setEmail(null);
                loginResponse.setSuccess(false);
                log.info("Email doesn't exist");
                return loginResponse;
            }
            if(user.getLocked() && currentDate.getTime()-user.getFailedTime().getTime()<lockDuration){

                log.info("Your account is Locked.Try after 5 min.");
                LoginResponse loginResponse=new LoginResponse();
                loginResponse.setMessage("Your account is Locked!Try after 5 min.");
                loginResponse.setJwt(null);
                loginResponse.setEmail(null);
                loginResponse.setSuccess(false);
                return loginResponse;
            }
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail().toLowerCase(), loginRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                String jwt = (jwtService.generateToken(loginRequest.getEmail().toLowerCase()));
                user.setLocked(false);
                user.setFailedTime(null);
                LoginAttempt loginAttempt=loginAttemptRepository.findByUserId(user);
                if(loginAttempt!=null) {
                    loginAttempt.setUser(null);
                    loginAttemptRepository.save(loginAttempt);
                    loginAttemptRepository.delete(loginAttempt);
                }
                userRepository.save(user);
                LoginResponse loginResponse=new LoginResponse();
                loginResponse.setMessage("Login Successfull!");
                loginResponse.setJwt(jwt);
                loginResponse.setEmail(loginRequest.getEmail().toLowerCase());
                loginResponse.setRole(user.getRole());
                loginResponse.setSuccess(true);
                log.info("Login Successfull");
                return loginResponse;
            }
            else {
                LoginResponse loginResponse=new LoginResponse();
                loginResponse.setMessage("Wrong Password");
                loginResponse.setJwt(null);
                loginResponse.setEmail(null);
                loginResponse.setSuccess(false);
                log.info("Wrong Password");
                return loginResponse;
            }

        }
        catch (Exception e){
            log.error("Wrong Password",e);
            LoginAttempt loginAttempt=loginAttemptRepository.findByUserId(user);
            long lastAttemptTime =lockDuration+1;
            if(loginAttempt!=null){
                Date lastAttempt = loginAttempt.getTime();
                lastAttemptTime = currentDate.getTime() - lastAttempt.getTime();
            }
            if(loginAttempt==null || loginAttempt.getFailedAttempt()==0 || lastAttemptTime>continiousAttempDuration){
                if(loginAttempt==null){
                    loginAttempt=new LoginAttempt();
                    loginAttempt.setUser(user);
                }
                loginAttempt.setFailedAttempt(1);
                loginAttempt.setTime(currentDate);
                loginAttemptRepository.save(loginAttempt);
                LoginResponse loginResponse=new LoginResponse();
                loginResponse.setMessage("Wrong Password,Only 2 attempts left");
                loginResponse.setJwt(null);
                loginResponse.setEmail(null);
                loginResponse.setSuccess(false);
                return loginResponse;
            }
            if(loginAttempt.getFailedAttempt()==1 && lastAttemptTime<continiousAttempDuration){
                loginAttempt.setFailedAttempt(2);
                loginAttempt.setTime(currentDate);
                loginAttemptRepository.save(loginAttempt);
                log.error("Wrong Password,Only 1 attempt left");
                LoginResponse loginResponse=new LoginResponse();
                loginResponse.setMessage("Wrong Password,Only 1 attempt left");
                loginResponse.setJwt(null);
                loginResponse.setEmail(null);
                loginResponse.setSuccess(false);
                return loginResponse;
            }
            if(loginAttempt.getFailedAttempt()==2 && lastAttemptTime<continiousAttempDuration){
                user.setFailedTime(currentDate);
                user.setLocked(true);
                loginAttempt.setUser(null);
                loginAttemptRepository.save(loginAttempt);
                loginAttemptRepository.delete(loginAttempt);
                userRepository.save(user);
                log.error("Due to too many wrong attempts your account is locked for 5 min.");
                LoginResponse loginResponse=new LoginResponse();
                loginResponse.setMessage("Too many wrong attempts your account is locked for 5 min.");
                loginResponse.setJwt(null);
                loginResponse.setEmail(null);
                loginResponse.setSuccess(false);
                return loginResponse;
            }
            throw new UserException("Wrong Password");
        }
    }

    @Override
    public ResponseEntity<User> userData(EmailRequest emailRequest) {
        User user=userRepository.findByEmail(emailRequest.getEmail());
        if(user==null){
            return null;
        }
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

}
