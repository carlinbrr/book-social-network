package com.bsn.api.core.value;

import java.util.Objects;

public class AuthorName {

    public static final int MAX_LENGTH = 255;

    private final String value;


    public String getValue() {
        return value;
    }


    public AuthorName(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Author name cannot be null or empty");
        }

        String normalizedAuthorName = value.trim();

        if (normalizedAuthorName.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("Author name is too long. Exceeds " + MAX_LENGTH + " characters");
        }

        this.value = normalizedAuthorName;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        AuthorName that = (AuthorName) o;
        return Objects.equals(value, that.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

}
