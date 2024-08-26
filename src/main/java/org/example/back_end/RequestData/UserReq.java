package org.example.back_end.RequestData;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserReq {
    private String accountName;
    private String password;
    private int otp;
}
