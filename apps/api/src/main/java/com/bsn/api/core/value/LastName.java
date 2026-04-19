package com.bsn.api.core.value;

import java.util.Objects;

public class LastName {

    public static final int MAX_LENGTH = 255;

    private final String value;


    public String getValue() {
        return value;
    }


    public LastName(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Last name cannot be null or empty");
        }

        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("Last name is too long. Exceeds " +  MAX_LENGTH + " characters");
        }

        this.value = value.trim();
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        LastName lastName = (LastName) o;
        return Objects.equals(value, lastName.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

}
