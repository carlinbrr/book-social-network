package com.bsn.api.core.value;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LastNameTest {

    @Test
    @DisplayName("Given a valid input when new then last name is created")
    public void givenValidInput_whenNew_thenLastNameIsCreated() {
        String value = "Doe";

        LastName lastName = new LastName(value);

        assertEquals("Doe", lastName.getValue());
    }

    @Test
    @DisplayName("Given an input with spaces when new then last name is created")
    public void givenInputWithSpaces_whenNew_thenLastNameIsCreated() {
        String value = " Doe     ";

        LastName lastName = new LastName(value);

        assertEquals("Doe", lastName.getValue());
    }

    @Test
    @DisplayName("Given a null input when new then IllegalArgumentException is thrown")
    public void givenNullInput_whenNew_thenIllegalArgumentExceptionIsThrown() {
        try {
            new LastName(null);
        } catch (IllegalArgumentException e) {
            assertEquals("Last name cannot be null or empty", e.getMessage());
            return;
        }

        fail();
    }

    @Test
    @DisplayName("Given an empty input when new then IllegalArgumentException is thrown")
    public void givenEmptyInput_whenNew_thenIllegalArgumentExceptionIsThrown() {
        String value = "";

        try {
            new LastName(value);
        } catch (IllegalArgumentException e) {
            assertEquals("Last name cannot be null or empty", e.getMessage());
            return;
        }

        fail();
    }

    @Test
    @DisplayName("Given a blank input when new then IllegalArgumentException is thrown")
    public void givenBlankInput_whenNew_thenIllegalArgumentExceptionIsThrown() {
        String value = "     ";

        try {
            new LastName(value);
        } catch (IllegalArgumentException e) {
            assertEquals("Last name cannot be null or empty", e.getMessage());
            return;
        }

        fail();
    }

    @Test
    @DisplayName("Given a too long input when new then IllegalArgumentException is thrown")
    public void givenTooLongInput_whenNew_thenIllegalArgumentExceptionIsThrown() {
        char[] array = new char[LastName.MAX_LENGTH + 1];
        int pos = 0;
        while (pos < array.length) {
            array[pos] = 'a';
            pos++;
        }
        String value = new String(array);

        try {
            new LastName(value);
        } catch (IllegalArgumentException e) {
            assertEquals("Last name is too long. Exceeds " + LastName.MAX_LENGTH + " characters", e.getMessage());
            return;
        }

        fail();
    }

    @Test
    @DisplayName("Given same inputs when equals then last names are equal")
    public void givenSameInputs_whenEquals_thenLastNamesAreEqual() {
        LastName lastName1 = new LastName("Doe");
        LastName lastName2 = new LastName("Doe");

        assertEquals(lastName1, lastName2);
    }

    @Test
    @DisplayName("Given inputs with different spaces when equals then last names are equal")
    public void givenInputsWithDifferentSpaces_whenEquals_thenLastNamesAreEqual() {
        LastName lastName1 = new LastName("Doe");
        LastName lastName2 = new LastName(" Doe      ");

        assertEquals(lastName1, lastName2);
    }

    @Test
    @DisplayName("Given different inputs when equals then last names are different")
    public void givenDifferentInputs_whenEquals_thenLastNamesAreDifferent() {
        LastName lastName1 = new LastName("Doe");
        LastName lastName2 = new LastName("Pérez");

        assertNotEquals(lastName1, lastName2);
    }

}
