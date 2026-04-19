package com.bsn.api.core.value;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookCoverTest {

    @Test
    @DisplayName("Given a valid input when new then book cover is created")
    public void givenValidInput_whenNew_thenBookCoverIsCreated() {
        String path = "/temp/img/profile.png";

        BookCover BookCover = new BookCover(path);

        assertEquals("/temp/img/profile.png", BookCover.getPath());
    }

    @Test
    @DisplayName("Given a null input when new then IllegalArgumentException is thrown")
    public void givenNullInput_whenNew_thenIllegalArgumentExceptionIsThrown() {
        try {
            new BookCover(null);
        } catch (IllegalArgumentException e) {
            assertEquals("Book cover cannot be null or empty", e.getMessage());
            return;
        }

        fail("IllegalArgumentException should have been thrown");
    }

    @Test
    @DisplayName("Given an empty input when new then IllegalArgumentException is thrown")
    public void givenEmptyInput_whenNew_thenIllegalArgumentExceptionIsThrown() {
        String value = "";

        try {
            new BookCover(value);
        } catch (IllegalArgumentException e) {
            assertEquals("Book cover cannot be null or empty", e.getMessage());
            return;
        }

        fail("IllegalArgumentException should have been thrown");
    }

    @Test
    @DisplayName("Given a blank input when new then IllegalArgumentException is thrown")
    public void givenBlankInput_whenNew_thenIllegalArgumentExceptionIsThrown() {
        String value = "     ";

        try {
            new BookCover(value);
        } catch (IllegalArgumentException e) {
            assertEquals("Book cover cannot be null or empty", e.getMessage());
            return;
        }

        fail("IllegalArgumentException should have been thrown");
    }

    @Test
    @DisplayName("Given same inputs when equals then book covers are equal")
    public void givenSameInputs_whenEquals_thenFirstNamesAreEqual() {
        BookCover bookCover1 = new BookCover("/temp/img/profile.png");
        BookCover bookCover2 = new BookCover("/temp/img/profile.png");

        assertEquals(bookCover1, bookCover2);
    }

    @Test
    @DisplayName("Given different inputs when equals then book covers are different")
    public void givenDifferentInputs_whenEquals_thenFirstNamesAreDifferent() {
        BookCover bookCover1 = new BookCover("/temp/img/profile.png");
        BookCover bookCover2 = new BookCover("/temp/img/profile2.png");

        assertNotEquals(bookCover1, bookCover2);
    }

}
