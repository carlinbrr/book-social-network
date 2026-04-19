package com.bsn.api.core.value;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EmailTest {

    @Test
    @DisplayName("Given a valid input when new then email is created")
    public void givenValidInput_whenNew_thenEmailIsCreated() {
        String value = "john@mail.com";

        Email email = new Email(value);

        assertEquals("john@mail.com", email.getValue());
    }

    @Test
    @DisplayName("Given an input with uppercase and spaces when new then email is normalized and created")
    public void givenInputWithUppercaseAndSpaces_whenNew_thenEmailIsNormalizedAndCreated() {
        String value = "    John@mail.COM ";

        Email email = new Email(value);

        assertEquals("john@mail.com",  email.getValue());
    }

    @Test
    @DisplayName("Given a null input when new then IllegalArgumentException is thrown")
    public void givenNullInput_whenNew_thenIllegalArgumentExceptionIsThrown() {
        try {
            new Email(null);
        } catch (IllegalArgumentException e) {
            assertEquals("Email cannot be null or empty", e.getMessage());
            return;
        }

        fail();
    }

    @Test
    @DisplayName("Given an empty input when new then IllegalArgumentException is thrown")
    public void givenEmptyInput_whenNew_thenIllegalArgumentExceptionIsThrown() {
        String value = "";

        try {
            new Email(value);
        } catch (IllegalArgumentException e) {
            assertEquals("Email cannot be null or empty", e.getMessage());
            return;
        }

        fail();
    }

    @Test
    @DisplayName("Given a blank input when new then IllegalArgumentException is thrown")
    public void givenBlankInput_whenNew_thenIllegalArgumentExceptionIsThrown() {
        String value = "     ";

        try {
            new Email(value);
        } catch (IllegalArgumentException e) {
            assertEquals("Email cannot be null or empty", e.getMessage());
            return;
        }

        fail();
    }

    @Test
    @DisplayName("Given a too long input when new then IllegalArgumentException is thrown")
    public void givenTooLongInput_whenNew_thenIllegalArgumentExceptionIsThrown() {
        char[] array = new char[Email.MAX_LENGTH + 1];
        int pos = 0;
        while (pos < array.length) {
            array[pos] = 'a';
            pos++;
        }
        String value = new String(array);

        try {
            new Email(value);
        } catch (IllegalArgumentException e) {
            assertEquals("Email is too long. Exceeds " + Email.MAX_LENGTH + " characters", e.getMessage());
            return;
        }

        fail();
    }

    @Test
    @DisplayName("Given an invalid input format when new then IllegalArgumentException is thrown")
    public void givenInvalidInputFormat_whenNew_thenIllegalArgumentExceptionIsThrown() {
        String value = "john";

        try {
            new Email(value);
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid email format", e.getMessage());
            return;
        }

        fail();
    }

    @Test
    @DisplayName("Given an input without domain when new then IllegalArgumentException is thrown")
    public void givenInputWithoutDomain_whenNew_thenIllegalArgumentExceptionIsThrown() {
        String value = "john@mail";

        try {
            new Email(value);
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid email format", e.getMessage());
            return;
        }

        fail();
    }

    @Test
    @DisplayName("Given an input without '@' when new then IllegalArgumentException is thrown")
    public void givenInputWithoutAtSymbol_whenNew_thenIllegalArgumentExceptionIsThrown() {
        String value = "john.mail.com";

        try {
            new Email(value);
        } catch (IllegalArgumentException e) {
            assertEquals("Invalid email format", e.getMessage());
            return;
        }

        fail();
    }

    @Test
    @DisplayName("Given same inputs when equals then emails are equal")
    public void givenSameInputs_whenEquals_thenEmailsAreEqual() {
        Email email1 = new Email("john@mail.com");
        Email email2 = new Email("john@mail.com");

        assertEquals(email1, email2);
    }

    @Test
    @DisplayName("Given inputs with different case and spaces when equals then emails are equal")
    public void givenInputsWithDifferentCaseAndSpaces_whenEquals_thenEmailsAreEqual() {
        Email email1 =  new Email("john@mail.com");
        Email email2 =  new Email(" JOHN@mail.com   ");

        assertEquals(email1, email2);
    }

    @Test
    @DisplayName("Given different inputs when equals then emails are different")
    public void givenDifferentInputs_whenEquals_thenEmailsAreDifferent() {
        Email email1 = new Email("john@mail.com");
        Email email2 = new Email("juan@mail.com");

        assertNotEquals(email1, email2);
    }

}
