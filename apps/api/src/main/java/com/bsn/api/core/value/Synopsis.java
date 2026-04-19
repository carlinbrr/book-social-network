package com.bsn.api.core.value;

import java.util.Objects;

public class Synopsis {

    public static final int MAX_LENGTH = 1025;

    private final String value;


    public String getValue() {
        return value;
    }


    public Synopsis(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Synopsis cannot be null or empty");
        }

        String normalizedSynopsis = value.trim();

        if (normalizedSynopsis.length() > MAX_LENGTH) {
            throw new IllegalArgumentException("Synopsis is too long. Exceeds " +  MAX_LENGTH + " characters");
        }

        this.value = normalizedSynopsis;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Synopsis synopsis = (Synopsis) o;
        return Objects.equals(value, synopsis.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

}
