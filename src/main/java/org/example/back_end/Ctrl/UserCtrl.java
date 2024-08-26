package org.example.back_end.Ctrl;

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
}
