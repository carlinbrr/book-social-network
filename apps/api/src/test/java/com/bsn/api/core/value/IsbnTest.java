package com.bsn.api.core.value;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IsbnTest {

    @Test
    @DisplayName("Given a valid input when new then isbn is created")
    public void givenValidInput_whenNew_thenIsbnIsCreated() {
        String value = "1234567890";

        Isbn Isbn = new Isbn(value);

        assertEquals("1234567890", Isbn.getValue());
    }

    @Test
    @DisplayName("Given an input with spaces when new then isbn is created")
    public void givenInputWithSpaces_whenNew_thenIsbnIsCreated() {
        String value = " 1234567890     ";

        Isbn Isbn = new Isbn(value);

        assertEquals("1234567890", Isbn.getValue());
    }

    @Test
    @DisplayName("Given a null input when new then IllegalArgumentException is thrown")
    public void givenNullInput_whenNew_thenIllegalArgumentExceptionIsThrown() {
        try {
            new Isbn(null);
        } catch (IllegalArgumentException e) {
            assertEquals("Isbn cannot be null or empty", e.getMessage());
            return;
        }

        fail("IllegalArgumentException should have been thrown");
    }

    @Test
    @DisplayName("Given an empty input when new then IllegalArgumentException is thrown")
    public void givenEmptyInput_whenNew_thenIllegalArgumentExceptionIsThrown() {
        String value = "";

        try {
            new Isbn(value);
        } catch (IllegalArgumentException e) {
            assertEquals("Isbn cannot be null or empty", e.getMessage());
            return;
        }

        fail("IllegalArgumentException should have been thrown");
    }

    @Test
    @DisplayName("Given a blank input when new then IllegalArgumentException is thrown")
    public void givenBlankInput_whenNew_thenIllegalArgumentExceptionIsThrown() {
        String value = "     ";

        try {
            new Isbn(value);
        } catch (IllegalArgumentException e) {
            assertEquals("Isbn cannot be null or empty", e.getMessage());
            return;
        }

        fail("IllegalArgumentException should have been thrown");
    }

    @Test
    @DisplayName("Given a too long input when new then IllegalArgumentException is thrown")
    public void givenTooLongInput_whenNew_thenIllegalArgumentExceptionIsThrown() {
        char[] array = new char[Isbn.MAX_LENGTH + 1];
        int pos = 0;
        while (pos < array.length) {
            array[pos] = 'a';
            pos++;
        }
        String value = new String(array);

        try {
            new Isbn(value);
        } catch (IllegalArgumentException e) {
            assertEquals("Isbn is too long. Exceeds " + Isbn.MAX_LENGTH + " characters", e.getMessage());
            return;
        }

        fail("IllegalArgumentException should have been thrown");
    }

    @Test
    @DisplayName("Given same inputs when equals then isbns are equal")
    public void givenSameInputs_whenEquals_thenIsbnsAreEqual() {
        Isbn isbn1 = new Isbn("1234567890");
        Isbn isbn2 = new Isbn("1234567890");

        assertEquals(isbn1, isbn2);
    }

    @Test
    @DisplayName("Given inputs with different spaces when equals then isbns are equal")
    public void givenInputsWithDifferentSpaces_whenEquals_thenIsbnsAreEqual() {
        Isbn isbn1 = new Isbn("1234567890");
        Isbn isbn2 = new Isbn(" 1234567890      ");

        assertEquals(isbn1, isbn2);
    }

    @Test
    @DisplayName("Given different inputs when equals then isbns are different")
    public void givenDifferentInputs_whenEquals_thenIsbnsAreDifferent() {
        Isbn isbn1 = new Isbn("1234567890");
        Isbn isbn2 = new Isbn("0987654321");

        assertNotEquals(isbn1, isbn2);
    }

}
