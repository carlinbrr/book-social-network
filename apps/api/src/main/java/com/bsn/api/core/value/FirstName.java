package com.bsn.api.core.value;

import java.util.Objects;

public class FirstName {

    public static final int MAX_LENGTH = 255;

    private final String value;


    public String getValue() {
        return value;
    }


    public FirstName(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("First name cannot be null or empty");
        }

        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("First name is too long. Exceeds " +  MAX_LENGTH + " characters");
        }

        this.value = value.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FirstName firstName = (FirstName) o;
        return Objects.equals(value, firstName.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

}
