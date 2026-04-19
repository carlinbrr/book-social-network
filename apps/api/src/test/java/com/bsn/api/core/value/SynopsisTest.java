package com.bsn.api.core.value;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SynopsisTest {

    @Test
    @DisplayName("Given a valid input when new then synopsis is created")
    public void givenValidInput_whenNew_thenSynopsisIsCreated() {
        String value = "Synopsis...";

        Synopsis Synopsis = new Synopsis(value);

        assertEquals("Synopsis...", Synopsis.getValue());
    }

    @Test
    @DisplayName("Given an input with spaces when new then synopsis is created")
    public void givenInputWithSpaces_whenNew_thenSynopsisIsCreated() {
        String value = " Synopsis...     ";

        Synopsis Synopsis = new Synopsis(value);

        assertEquals("Synopsis...", Synopsis.getValue());
    }

    @Test
    @DisplayName("Given a null input when new then IllegalArgumentException is thrown")
    public void givenNullInput_whenNew_thenIllegalArgumentExceptionIsThrown() {
        try {
            new Synopsis(null);
        } catch (IllegalArgumentException e) {
            assertEquals("Synopsis cannot be null or empty", e.getMessage());
            return;
        }

        fail("IllegalArgumentException should have been thrown");
    }

    @Test
    @DisplayName("Given an empty input when new then IllegalArgumentException is thrown")
    public void givenEmptyInput_whenNew_thenIllegalArgumentExceptionIsThrown() {
        String value = "";

        try {
            new Synopsis(value);
        } catch (IllegalArgumentException e) {
            assertEquals("Synopsis cannot be null or empty", e.getMessage());
            return;
        }

        fail("IllegalArgumentException should have been thrown");
    }

    @Test
    @DisplayName("Given a blank input when new then IllegalArgumentException is thrown")
    public void givenBlankInput_whenNew_thenIllegalArgumentExceptionIsThrown() {
        String value = "     ";

        try {
            new Synopsis(value);
        } catch (IllegalArgumentException e) {
            assertEquals("Synopsis cannot be null or empty", e.getMessage());
            return;
        }

        fail("IllegalArgumentException should have been thrown");
    }

    @Test
    @DisplayName("Given a too long input when new then IllegalArgumentException is thrown")
    public void givenTooLongInput_whenNew_thenIllegalArgumentExceptionIsThrown() {
        char[] array = new char[Synopsis.MAX_LENGTH + 1];
        int pos = 0;
        while (pos < array.length) {
            array[pos] = 'a';
            pos++;
        }
        String value = new String(array);

        try {
            new Synopsis(value);
        } catch (IllegalArgumentException e) {
            assertEquals("Synopsis is too long. Exceeds " + Synopsis.MAX_LENGTH + " characters", e.getMessage());
            return;
        }

        fail("IllegalArgumentException should have been thrown");
    }

    @Test
    @DisplayName("Given same inputs when equals then synopsis are equal")
    public void givenSameInputs_whenEquals_thenSynopsisAreEqual() {
        Synopsis synopsis1 = new Synopsis("Synopsis...");
        Synopsis synopsis2 = new Synopsis("Synopsis...");

        assertEquals(synopsis1, synopsis2);
    }

    @Test
    @DisplayName("Given inputs with different spaces when equals then synopsis are equal")
    public void givenInputsWithDifferentSpaces_whenEquals_thenSynopsisAreEqual() {
        Synopsis synopsis1 = new Synopsis("Synopsis...");
        Synopsis synopsis2 = new Synopsis(" Synopsis...      ");

        assertEquals(synopsis1, synopsis2);
    }

    @Test
    @DisplayName("Given different inputs when equals then synopsis are different")
    public void givenDifferentInputs_whenEquals_thenSynopsisAreDifferent() {
        Synopsis synopsis1 = new Synopsis("Synopsis...");
        Synopsis synopsis2 = new Synopsis("Another Synopsis...");

        assertNotEquals(synopsis1, synopsis2);
    }

}
