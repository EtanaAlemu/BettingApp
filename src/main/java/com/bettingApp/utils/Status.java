package com.bettingApp.utils;

import java.util.Arrays;

public enum Status {
    INITIAL,
    ACTIVE,
    BLOCKED,
    BANNED,
    DELETED;

    public static Status lookup(String status) {
        return Arrays.stream(Status.values())
                .filter(e -> e.name().equalsIgnoreCase(status)).findAny()
                .orElseThrow(() -> new IllegalArgumentException("Invalid Status."));
    }
}
