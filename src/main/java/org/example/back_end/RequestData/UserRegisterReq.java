package org.example.back_end.RequestData;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class UserRegisterReq {
    private String userName;

    @ToString.Exclude
    private String password;

    private String firstName;
    private String lastName;
    private String base32SecretKey;
    private Integer otp;
}
