package com.bsn.api.core.value;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TitleTest {

    @Test
    @DisplayName("Given a valid input when new then title is created")
    public void givenValidInput_whenNew_thenTitleIsCreated() {
        String value = "Clean Code";

        Title Title = new Title(value);

        assertEquals("Clean Code", Title.getValue());
    }

    @Test
    @DisplayName("Given an input with spaces when new then title is created")
    public void givenInputWithSpaces_whenNew_thenTitleIsCreated() {
        String value = " Clean Code     ";

        Title Title = new Title(value);

        assertEquals("Clean Code", Title.getValue());
    }

    @Test
    @DisplayName("Given a null input when new then IllegalArgumentException is thrown")
    public void givenNullInput_whenNew_thenIllegalArgumentExceptionIsThrown() {
        try {
            new Title(null);
        } catch (IllegalArgumentException e) {
            assertEquals("Title cannot be null or empty", e.getMessage());
            return;
        }

        fail("IllegalArgumentException should have been thrown");
    }

    @Test
    @DisplayName("Given an empty input when new then IllegalArgumentException is thrown")
    public void givenEmptyInput_whenNew_thenIllegalArgumentExceptionIsThrown() {
        String value = "";

        try {
            new Title(value);
        } catch (IllegalArgumentException e) {
            assertEquals("Title cannot be null or empty", e.getMessage());
            return;
        }

        fail("IllegalArgumentException should have been thrown");
    }

    @Test
    @DisplayName("Given a blank input when new then IllegalArgumentException is thrown")
    public void givenBlankInput_whenNew_thenIllegalArgumentExceptionIsThrown() {
        String value = "     ";

        try {
            new Title(value);
        } catch (IllegalArgumentException e) {
            assertEquals("Title cannot be null or empty", e.getMessage());
            return;
        }

        fail("IllegalArgumentException should have been thrown");
    }

    @Test
    @DisplayName("Given a too long input when new then IllegalArgumentException is thrown")
    public void givenTooLongInput_whenNew_thenIllegalArgumentExceptionIsThrown() {
        char[] array = new char[Title.MAX_LENGTH + 1];
        int pos = 0;
        while (pos < array.length) {
            array[pos] = 'a';
            pos++;
        }
        String value = new String(array);

        try {
            new Title(value);
        } catch (IllegalArgumentException e) {
            assertEquals("Title is too long. Exceeds " + Title.MAX_LENGTH + " characters", e.getMessage());
            return;
        }

        fail("IllegalArgumentException should have been thrown");
    }

    @Test
    @DisplayName("Given same inputs when equals then titles are equal")
    public void givenSameInputs_whenEquals_thenTitlesAreEqual() {
        Title title1 = new Title("Clean Code");
        Title title2 = new Title("Clean Code");

        assertEquals(title1, title2);
    }

    @Test
    @DisplayName("Given inputs with different spaces when equals then titles are equal")
    public void givenInputsWithDifferentSpaces_whenEquals_thenTitlesAreEqual() {
        Title title1 = new Title("Clean Code");
        Title title2 = new Title(" Clean Code      ");

        assertEquals(title1, title2);
    }

    @Test
    @DisplayName("Given different inputs when equals then titles are different")
    public void givenDifferentInputs_whenEquals_thenTitlesAreDifferent() {
        Title title1 = new Title("Clean Code");
        Title title2 = new Title("Clean Architecture");

        assertNotEquals(title1, title2);
    }

}
