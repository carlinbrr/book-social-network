package com.bsn.api.core.value;

import java.util.Objects;

public class UserId {

    public static final int MAX_LENGTH = 255;

    private final String value;


    public String getValue() {
        return value;
    }


    public UserId(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("User id cannot be null or empty");
        }

        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("User id is too long. Exceeds " + MAX_LENGTH + " characters");
        }

        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        UserId userId = (UserId) o;
        return Objects.equals(value, userId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

}
