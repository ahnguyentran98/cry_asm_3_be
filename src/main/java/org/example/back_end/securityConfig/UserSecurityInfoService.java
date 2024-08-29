package org.example.back_end.securityConfig;

import lombok.RequiredArgsConstructor;
import org.example.back_end.Entity.User;
import org.example.back_end.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserSecurityInfoService implements UserDetailsService {
    @Autowired
    private UserService userService;


    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
        User userInfo = userService.getUserByUserName(userName);
        if (userInfo == null){
            throw new UsernameNotFoundException("User not found with user name: " + userName);
        }
        return new UserSecurityDetails(userInfo);
    }
}
