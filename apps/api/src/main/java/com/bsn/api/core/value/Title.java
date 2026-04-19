package com.bsn.api.core.value;

import java.util.Objects;

public class Title {

    public static final int MAX_LENGTH = 255;

    private final String value;


    public String getValue() {
        return value;
    }


    public Title(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Title cannot be null or empty");
        }

        String normalizedTitle = value.trim();

        if (normalizedTitle.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("Title is too long. Exceeds " +  MAX_LENGTH + " characters");
        }

        this.value = normalizedTitle;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Title title = (Title) o;
        return Objects.equals(value, title.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

}
