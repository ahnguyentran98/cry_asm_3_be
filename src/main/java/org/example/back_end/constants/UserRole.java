package org.example.back_end.constants;

import lombok.Getter;

@Getter
public enum UserRole {
    USER("USER"),
    ADMIN("ADMIN");


    private final String role;

    UserRole(String role) {
        this.role = role;
    }
}
