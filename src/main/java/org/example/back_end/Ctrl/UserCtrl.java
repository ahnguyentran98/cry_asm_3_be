package org.example.back_end.Ctrl;

import org.example.back_end.RequestData.UserRegisterReq;
import org.example.back_end.RequestData.UserReq;
import org.example.back_end.ResponseData.UserRes;
import org.example.back_end.Services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("/api/user")
public class UserCtrl {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public UserRes login(@RequestBody UserReq userReq){
        if (StringUtils.isBlank(userReq.getAccountName()) || StringUtils.isBlank(userReq.getPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request data");
        }

        return userService.validateUserLogin(userReq);
    }

    @PostMapping("/login-otp")
    public UserRes loginOTP(@RequestBody UserReq userReq){
        if (StringUtils.isBlank(userReq.getAccountName()) || StringUtils.isBlank(userReq.getPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid request data");
        }

        return userService.validateUserOTPLogin(userReq);
    }

    @PostMapping("/register")
    public String registerAndGetBase32SecretKey(@RequestBody UserRegisterReq userRegisterReq){
        // Check if accountName is blank
        if (StringUtils.isBlank(userRegisterReq.getAccountName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account name is required");
        }

        // Check if password is blank
        if (StringUtils.isBlank(userRegisterReq.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is required");
        }

        // Check if firstName is blank
        if (StringUtils.isBlank(userRegisterReq.getFirstName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "First name is required");
        }

        // Check if lastName is blank
        if (StringUtils.isBlank(userRegisterReq.getLastName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Last name is required");
        }

        return userService.validateUserRegisterAndGetSecretKey(userRegisterReq);
    }

    @PostMapping("/register-otp")
    public UserRes registerOTP(@RequestBody UserRegisterReq userRegisterReq){
        // Check if accountName is blank
        if (StringUtils.isBlank(userRegisterReq.getAccountName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Account name is required");
        }

        // Check if password is blank
        if (StringUtils.isBlank(userRegisterReq.getPassword())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Password is required");
        }

        // Check if firstName is blank
        if (StringUtils.isBlank(userRegisterReq.getFirstName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "First name is required");
        }

        // Check if lastName is blank
        if (StringUtils.isBlank(userRegisterReq.getLastName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Last name is required");
        }

        // Check if base32SecretKey is blank
        if (StringUtils.isBlank(userRegisterReq.getBase32SecretKey())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Base32 secret key is required");
        }

        // Check if otp is null
        if (userRegisterReq.getOtp() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "OTP is required");
        }

        return userService.validateOTPAndRegister(userRegisterReq);
    }
}
