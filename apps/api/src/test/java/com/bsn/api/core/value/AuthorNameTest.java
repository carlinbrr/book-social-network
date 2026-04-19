package com.bsn.api.core.value;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AuthorNameTest {

    @Test
    @DisplayName("Given a valid input when new then author name is created")
    public void givenValidInput_whenNew_thenAuthorNameIsCreated() {
        String value = "Robert C. Martin";

        AuthorName AuthorName = new AuthorName(value);

        assertEquals("Robert C. Martin", AuthorName.getValue());
    }

    @Test
    @DisplayName("Given an input with spaces when new then author name is created")
    public void givenInputWithSpaces_whenNew_thenAuthorNameIsCreated() {
        String value = " Robert C. Martin     ";

        AuthorName AuthorName = new AuthorName(value);

        assertEquals("Robert C. Martin", AuthorName.getValue());
    }

    @Test
    @DisplayName("Given a null input when new then IllegalArgumentException is thrown")
    public void givenNullInput_whenNew_thenIllegalArgumentExceptionIsThrown() {
        try {
            new AuthorName(null);
        } catch (IllegalArgumentException e) {
            assertEquals("Author name cannot be null or empty", e.getMessage());
            return;
        }

        fail("IllegalArgumentException should have been thrown");
    }

    @Test
    @DisplayName("Given an empty input when new then IllegalArgumentException is thrown")
    public void givenEmptyInput_whenNew_thenIllegalArgumentExceptionIsThrown() {
        String value = "";

        try {
            new AuthorName(value);
        } catch (IllegalArgumentException e) {
            assertEquals("Author name cannot be null or empty", e.getMessage());
            return;
        }

        fail("IllegalArgumentException should have been thrown");
    }

    @Test
    @DisplayName("Given a blank input when new then IllegalArgumentException is thrown")
    public void givenBlankInput_whenNew_thenIllegalArgumentExceptionIsThrown() {
        String value = "     ";

        try {
            new AuthorName(value);
        } catch (IllegalArgumentException e) {
            assertEquals("Author name cannot be null or empty", e.getMessage());
            return;
        }

        fail("IllegalArgumentException should have been thrown");
    }

    @Test
    @DisplayName("Given a too long input when new then IllegalArgumentException is thrown")
    public void givenTooLongInput_whenNew_thenIllegalArgumentExceptionIsThrown() {
        char[] array = new char[AuthorName.MAX_LENGTH + 1];
        int pos = 0;
        while (pos < array.length) {
            array[pos] = 'a';
            pos++;
        }
        String value = new String(array);

        try {
            new AuthorName(value);
        } catch (IllegalArgumentException e) {
            assertEquals("Author name is too long. Exceeds " + AuthorName.MAX_LENGTH + " characters", e.getMessage());
            return;
        }

        fail("IllegalArgumentException should have been thrown");
    }

    @Test
    @DisplayName("Given same inputs when equals then author names are equal")
    public void givenSameInputs_whenEquals_thenFirstNamesAreEqual() {
        AuthorName authorName1 = new AuthorName("Robert C. Martin");
        AuthorName authorName2 = new AuthorName("Robert C. Martin");

        assertEquals(authorName1, authorName2);
    }

    @Test
    @DisplayName("Given inputs with different spaces when equals then author names are equal")
    public void givenInputsWithDifferentSpaces_whenEquals_thenFirstNamesAreEqual() {
        AuthorName authorName1 = new AuthorName("Robert C. Martin");
        AuthorName authorName2 = new AuthorName(" Robert C. Martin      ");

        assertEquals(authorName1, authorName2);
    }

    @Test
    @DisplayName("Given different inputs when equals then author names are different")
    public void givenDifferentInputs_whenEquals_thenFirstNamesAreDifferent() {
        AuthorName authorName1 = new AuthorName("Robert C. Martin");
        AuthorName authorName2 = new AuthorName("Roberto Martín");

        assertNotEquals(authorName1, authorName2);
    }

}
