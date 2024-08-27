package org.example.back_end.constants;

import lombok.Getter;

@Getter
public enum UserSecurityLabel {
    TOP_SECRET("TOP_SECRET"),
    SECRET("SECRET"),
    CONFIDENTIAL("CONFIDENTIAL"),
    UNCLASSIFIED("UNCLASSIFIED");


    private final String label;

    UserSecurityLabel(String label) {
        this.label = label;
    }
}
