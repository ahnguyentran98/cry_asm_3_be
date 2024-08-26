package org.example.back_end.Utils;

import com.warrenstrange.googleauth.GoogleAuthenticator;
import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import org.springframework.stereotype.Service;

@Service
public class OTPService {

    private final GoogleAuthenticator gAuth = new GoogleAuthenticator();

    public String generateBase32SecretKey() {
        GoogleAuthenticatorKey key = gAuth.createCredentials();
        return key.getKey();
    }

    public boolean verifyOTP(String secretKey, int userInputOtp) {
        return gAuth.authorize(secretKey, userInputOtp);
    }
}
