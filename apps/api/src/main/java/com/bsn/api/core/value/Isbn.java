package com.bsn.api.core.value;

import java.util.Objects;

public class Isbn {

    public static final int MAX_LENGTH = 255;

    private final String value;


    public String getValue() {
        return value;
    }


    public Isbn(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Isbn cannot be null or empty");
        }

        String normalizedIsbn = value.trim();

        if (normalizedIsbn.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("Isbn is too long. Exceeds " + MAX_LENGTH + " characters");
        }

        this.value = normalizedIsbn;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Isbn isbn = (Isbn) o;
        return Objects.equals(value, isbn.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

}
