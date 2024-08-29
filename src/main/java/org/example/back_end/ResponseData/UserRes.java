package org.example.back_end.ResponseData;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.example.back_end.Entity.User;

@Getter
@Setter
@ToString
public class UserRes {
    private Long id;
    private String accountName;
    private String firstName;
    private String lastName;
    private String role;
    private String label;
    private String jwt;

    public void fromUser(User user){
        this.id = user.getId();
        this.accountName = user.getAccountName();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.role = user.getRole();
        this.label = user.getLabel();
    }
}
