package com.bsn.api.core.value;

import java.util.Objects;

public class BookCover {

    private String path;


    public String getPath() {
        return path;
    }


    public BookCover(String path) {
        if (path == null || path.isBlank()) {
            throw new IllegalArgumentException("Book cover cannot be null or empty");
        }

        this.path = path;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        BookCover bookCover = (BookCover) o;
        return Objects.equals(path, bookCover.path);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(path);
    }

}
