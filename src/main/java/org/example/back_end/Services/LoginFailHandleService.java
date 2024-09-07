package org.example.back_end.Services;

import org.example.back_end.Entity.User;
import org.example.back_end.repositories.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LoginFailHandleService {
    @Autowired
    private UserRepo userRepo;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleFailedLogin(User user) {
        int failLogin = user.getFailLogin() + 1;
        if (failLogin > 5) {
            user.setLocked(true);
        }
        user.setFailLogin(failLogin);
        userRepo.saveAndFlush(user);
    }
}
