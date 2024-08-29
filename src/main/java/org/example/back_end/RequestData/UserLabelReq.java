package org.example.back_end.RequestData;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserLabelReq {
    private String accountName;
    private String label;
}
