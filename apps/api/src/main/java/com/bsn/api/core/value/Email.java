package com.bsn.api.core.value;

import java.util.Objects;

public class Email {

    public static final int MAX_LENGTH = 255;

    private static final String REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@" +
            "(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";

    private final String value;


    public String getValue() {
        return value;
    }


    public Email(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }

        String normalizedEmail = value.trim().toLowerCase();

        if (value.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("Email is too long. Exceeds " +  MAX_LENGTH + " characters");
        }

        if (!normalizedEmail.matches(REGEX)) {
            throw new  IllegalArgumentException("Invalid email format");
        }

        this.value = normalizedEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Email email = (Email) o;
        return Objects.equals(value, email.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

}
