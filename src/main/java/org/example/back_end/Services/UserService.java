package org.example.back_end.Services;

import org.example.back_end.Entity.User;
import org.example.back_end.RequestData.UserLabelReq;
import org.example.back_end.RequestData.UserRegisterReq;
import org.example.back_end.RequestData.UserReq;
import org.example.back_end.ResponseData.UserRes;
import org.example.back_end.Utils.KeyService;
import org.example.back_end.Utils.OTPService;
import org.example.back_end.constants.UserSecurityLabel;
import org.example.back_end.repositories.UserRepo;
import org.example.back_end.securityConfig.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class UserService {
    private final Logger LOGGER = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private KeyService keyService;

    @Autowired
    private OTPService otpService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private SecurityLevelService securityLevelService;

    @Transactional(readOnly = true)
    public User getUserByAccountName(String accountName){
        LOGGER.info("Get user by accountName {}", accountName);
        List<User> usersByAccountName = userRepo.getUserByAccountName(accountName);
        if (usersByAccountName.isEmpty()) {
            LOGGER.error("accountName is not existed");
            return null;
        }

        if (usersByAccountName.size() > 1) {
            LOGGER.error("Have {} account with same accountName {}", usersByAccountName.size(), accountName);
            return null;
        }

        return usersByAccountName.get(0);
    }

    @Transactional(readOnly = true)
    public User getUserById(Long userId){
        LOGGER.info("Get user by id {}", userId);
        User user = userRepo.getUserById(userId);
        if (user == null){
            LOGGER.info("User not found with id {}", userId);
            return null;
        }
        return user;
    }

    @Transactional(readOnly = true)
    public UserRes validateUserLogin(UserReq userReq){
        LOGGER.info("Validate user login {}", userReq);
        User user = this.getUserByAccountName(userReq.getAccountName());
        if (user == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid User");
        }

        if (!keyService.checkPassword(user.getPassword(), user.getPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid password");
        }

        if (user.isLocked()){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User is locked");
        }

        UserRes userRes = new UserRes();
        userRes.fromUser(user);

        return userRes;
    }

    @Transactional(readOnly = true)
    public UserRes validateUserOTPLogin(UserReq userReq){
        LOGGER.info("Validate user otp {}", userReq);
        User user = this.getUserByAccountName(userReq.getAccountName());
        if (user == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid User");
        }

        if(!otpService.verifyOTP(user.getOtpSecret(), userReq.getOtp())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid OTP");
        }

        UserRes userRes = new UserRes();
        userRes.fromUser(user);
        userRes.setJwt(tokenService.generateToken(user));

        return userRes;
    }

    @Transactional(readOnly = true)
    public String validateUserRegisterAndGetSecretKey(UserRegisterReq userRegisterReq){
        LOGGER.info("User register: {}", userRegisterReq);
        // validate existed user
        User user = this.getUserByAccountName(userRegisterReq.getAccountName());
        if (user != null){
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "User existed");
        }

        return otpService.generateBase32SecretKey();
    }

    @Transactional(rollbackFor = Exception.class)
    public UserRes validateOTPAndRegister(UserRegisterReq userRegisterReq){
        LOGGER.info("Validate OTP {} and register", userRegisterReq.getOtp());
        if (!otpService.verifyOTP(userRegisterReq.getBase32SecretKey(), userRegisterReq.getOtp())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid otp");
        }

        User user = new User();
        user.fromNewOne(userRegisterReq, keyService.hashPassword(userRegisterReq.getPassword()));
        userRepo.saveAndFlush(user);

        UserRes userRes = new UserRes();
        userRes.fromUser(user);
        return userRes;
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateUserLabel(UserLabelReq userLabelReq){
        LOGGER.info("Update user label: {}", userLabelReq);
        User user = this.getUserByAccountName(userLabelReq.getAccountName());
        if (user == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid user");
        }

        UserSecurityLabel userSecurityLabel = securityLevelService.getLevel(userLabelReq.getLabel());

        user.setLabel(userSecurityLabel.getLabel());
        userRepo.saveAndFlush(user);
    }
}
