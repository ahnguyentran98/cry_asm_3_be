package org.example.back_end.constants;

import lombok.Getter;

@Getter
public enum UserSecurityLabel {
    TOP_SECRET("TOP_SECRET", 1),
    SECRET("SECRET", 2),
    CONFIDENTIAL("CONFIDENTIAL", 3),
    UNCLASSIFIED("UNCLASSIFIED", 4);


    private final String label;
    private final Integer levelRank;

    UserSecurityLabel(String label, Integer levelRank) {
        this.label = label;
        this.levelRank = levelRank;
    }
}
