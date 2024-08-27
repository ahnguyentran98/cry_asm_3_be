package org.example.back_end.RequestData;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class UserReq {
    private String accountName;

    @ToString.Exclude
    private String password;

    private int otp;
}
