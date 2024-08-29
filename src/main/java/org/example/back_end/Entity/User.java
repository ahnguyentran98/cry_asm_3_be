package org.example.back_end.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.back_end.RequestData.UserRegisterReq;
import org.example.back_end.constants.UserRole;
import org.example.back_end.constants.UserSecurityLabel;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String accountName;

    private String firstName;

    private String lastName;

    private String role = UserRole.USER.getRole();

    private String password;

    private boolean isLocked = false;

    private Integer failLogin = 0;

    private String otpSecret;

    private String label = UserSecurityLabel.UNCLASSIFIED.getLabel();

    public void fromNewOne(UserRegisterReq req, String hashPassword){
        this.accountName = req.getAccountName();
        this.firstName = req.getFirstName();
        this.lastName = req.getLastName();
        this.otpSecret = req.getBase32SecretKey();
        this.password = hashPassword;
    }
}
