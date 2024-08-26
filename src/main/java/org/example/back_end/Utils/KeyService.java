package org.example.back_end.Utils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class KeyService {

    public String hashPassword(String plainPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.encode(plainPassword);
    }

    public boolean checkPassword(String plainPassword, String hashedPassword) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(plainPassword, hashedPassword);
    }


}
