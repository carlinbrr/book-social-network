package com.bsn.api.core.value;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserIdTest {

    @Test
    @DisplayName("Given a valid input when new then user id is created")
    public void givenValidInput_whenNew_thenUserIdIsCreated() {
        String value = "abc-123";

        UserId userId = new UserId(value);

        assertEquals("abc-123", userId.getValue());
    }

    @Test
    @DisplayName("Given a null input when new then IllegalArgumentException is thrown")
    public void givenNullInput_whenNew_thenIllegalArgumentExceptionIsThrown() {
        try {
            new UserId(null);
        } catch (IllegalArgumentException e) {
            assertEquals("User id cannot be null or empty", e.getMessage());
            return;
        }

        fail("IllegalArgumentException should have been thrown");
    }

    @Test
    @DisplayName("Given an empty input when new then IllegalArgumentException is thrown")
    public void givenEmptyInput_whenNew_thenIllegalArgumentExceptionIsThrown() {
        String value = "";

        try {
            new UserId(value);
        } catch (IllegalArgumentException e) {
            assertEquals("User id cannot be null or empty", e.getMessage());
            return;
        }

        fail("IllegalArgumentException should have been thrown");
    }

    @Test
    @DisplayName("Given a blank input when new then IllegalArgumentException is thrown")
    public void givenBlankInput_whenNew_thenIllegalArgumentExceptionIsThrown() {
        String value = "     ";

        try {
            new UserId(value);
        } catch (IllegalArgumentException e) {
            assertEquals("User id cannot be null or empty", e.getMessage());
            return;
        }

        fail("IllegalArgumentException should have been thrown");
    }

    @Test
    @DisplayName("Given a too long input when new then IllegalArgumentException is thrown")
    public void givenTooLongInput_whenNew_thenIllegalArgumentExceptionIsThrown() {
        char[] array = new char[UserId.MAX_LENGTH + 1];
        int pos = 0;
        while (pos < array.length) {
            array[pos] = '1';
            pos++;
        }
        String value = new String(array);

        try {
            new UserId(value);
        } catch (IllegalArgumentException e) {
            assertEquals("User id is too long. Exceeds " + UserId.MAX_LENGTH + " characters", e.getMessage());
            return;
        }

        fail("IllegalArgumentException should have been thrown");
    }

    @Test
    @DisplayName("Given same inputs when equals then user ids are equal")
    public void givenSameInputsWhitespaces_whenEquals_thenUserIdsAreEqual() {
        UserId userId1 = new UserId("abc-123");
        UserId userId2 = new UserId("abc-123");

        assertEquals(userId1, userId2);
    }

    @Test
    @DisplayName("Given different inputs when equals then user ids are different")
    public void givenDifferentInputs_whenEquals_thenUserIdsAreNotEqual() {
        UserId userId1 = new UserId("abc-123");
        UserId userId2 = new UserId("321-cba");

        assertNotEquals(userId1, userId2);
    }

}
