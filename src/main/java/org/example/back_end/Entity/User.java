package org.example.back_end.Entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private String accountName;

    private String firstName;

    private String lastName;

    private String role;

    private String password;

    private boolean isLocked;

    private Integer failLogin;

    private String otpSecret;

    private String label;

    @OneToMany(mappedBy = "user")
    private List<News> news;
}
