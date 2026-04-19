package com.bsn.api.core.value;

import java.util.Objects;

public class BookId {

    private Integer value;


    public Integer getValue() {
        return value;
    }


    public BookId(Integer value) {
        if (value == null) {
            throw new IllegalArgumentException("Book id cannot be null");
        }

        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BookId bookId = (BookId) o;
        return Objects.equals(value, bookId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(value);
    }

}
