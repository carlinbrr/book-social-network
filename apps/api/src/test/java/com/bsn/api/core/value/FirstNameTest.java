package com.bsn.api.core.value;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FirstNameTest {

    @Test
    @DisplayName("Given a valid input when new then first name is created")
    public void givenValidInput_whenNew_thenFirstNameIsCreated() {
        String value = "John";

        FirstName firstName = new FirstName(value);

        assertEquals("John", firstName.getValue());
    }

    @Test
    @DisplayName("Given an input with spaces when new then first name is created")
    public void givenInputWithSpaces_whenNew_thenFirstNameIsCreated() {
        String value = " John     ";

        FirstName firstName = new FirstName(value);

        assertEquals("John", firstName.getValue());
    }

    @Test
    @DisplayName("Given a null input when new then IllegalArgumentException is thrown")
    public void givenNullInput_whenNew_thenIllegalArgumentExceptionIsThrown() {
        try {
            new FirstName(null);
        } catch (IllegalArgumentException e) {
            assertEquals("First name cannot be null or empty", e.getMessage());
            return;
        }

        fail();
    }

    @Test
    @DisplayName("Given an empty input when new then IllegalArgumentException is thrown")
    public void givenEmptyInput_whenNew_thenIllegalArgumentExceptionIsThrown() {
        String value = "";

        try {
            new FirstName(value);
        } catch (IllegalArgumentException e) {
            assertEquals("First name cannot be null or empty", e.getMessage());
            return;
        }

        fail();
    }

    @Test
    @DisplayName("Given a blank input when new then IllegalArgumentException is thrown")
    public void givenBlankInput_whenNew_thenIllegalArgumentExceptionIsThrown() {
        String value = "     ";

        try {
            new FirstName(value);
        } catch (IllegalArgumentException e) {
            assertEquals("First name cannot be null or empty", e.getMessage());
            return;
        }

        fail();
    }

    @Test
    @DisplayName("Given a too long input when new then IllegalArgumentException is thrown")
    public void givenTooLongInput_whenNew_thenIllegalArgumentExceptionIsThrown() {
        char[] array = new char[FirstName.MAX_LENGTH + 1];
        int pos = 0;
        while (pos < array.length) {
            array[pos] = 'a';
            pos++;
        }
        String value = new String(array);

        try {
            new FirstName(value);
        } catch (IllegalArgumentException e) {
            assertEquals("First name is too long. Exceeds " + FirstName.MAX_LENGTH + " characters", e.getMessage());
            return;
        }

        fail();
    }

    @Test
    @DisplayName("Given same inputs when equals then first names are equal")
    public void givenSameInputs_whenEquals_thenFirstNamesAreEqual() {
        FirstName firstName1 = new FirstName("John");
        FirstName firstName2 = new FirstName("John");

        assertEquals(firstName1, firstName2);
    }

    @Test
    @DisplayName("Given inputs with different spaces when equals then first names are equal")
    public void givenInputsWithDifferentSpaces_whenEquals_thenFirstNamesAreEqual() {
        FirstName firstName1 = new FirstName("John");
        FirstName firstName2 = new FirstName(" John      ");

        assertEquals(firstName1, firstName2);
    }

    @Test
    @DisplayName("Given different inputs when equals then first names are different")
    public void givenDifferentInputs_whenEquals_thenFirstNamesAreDifferent() {
        FirstName firstName1 = new FirstName("John");
        FirstName firstName2 = new FirstName("Juan");

        assertNotEquals(firstName1, firstName2);
    }

}
