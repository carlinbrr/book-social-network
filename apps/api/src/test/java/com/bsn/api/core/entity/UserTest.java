package com.bsn.api.core.entity;

import com.bsn.api.core.value.Email;
import com.bsn.api.core.value.FirstName;
import com.bsn.api.core.value.LastName;
import com.bsn.api.core.value.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTest {

    private final UserId id = new UserId("abc-123");

    private final FirstName firstName = new FirstName("John");

    private final LastName lastName = new LastName("Doe");

    private final Email email = new Email("john@mail.com");


    @Test
    @DisplayName("Given a valid input when create new then user is created")
    public void givenValidInput_whenCreateNew_thenUserIsCreated() {
        User user = User.createNew(id, firstName, lastName, email);

        assertEquals(id, user.getId());
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(email, user.getEmail());
    }

    @Test
    @DisplayName("Given a null user id when create new then IllegalArgumentException is thrown")
    public void givenNullUserId_whenCreateNew_thenIllegalArgumentExceptionIsThrown() {
        try {
            User.createNew(null, firstName, lastName, email);
        } catch (IllegalArgumentException e) {
            assertEquals("User id cannot be null", e.getMessage());
            return;
        }

        fail();
    }

    @Test
    @DisplayName("Given a null email when create new then IllegalArgumentException is thrown")
    public void givenNullEmail_whenCreateNew_thenIllegalArgumentExceptionIsThrown() {
        try {
            User.createNew(id, firstName, lastName, null);
        } catch (IllegalArgumentException e) {
            assertEquals("Email cannot be null", e.getMessage());
            return;
        }

        fail();
    }

    @Test
    @DisplayName("Given a null first name when create new then IllegalArgumentException is thrown")
    public void givenNullFirstName_whenCreateNew_thenIllegalArgumentExceptionIsThrown() {
        try {
            User.createNew(id, null, lastName, email);
        } catch (IllegalArgumentException e) {
            assertEquals("First name cannot be null", e.getMessage());
            return;
        }

        fail();
    }

    @Test
    @DisplayName("Given a null last name when create new then IllegalArgumentException is thrown")
    public void givenNullLastName_whenCreateNew_thenIllegalArgumentExceptionIsThrown() {
        try {
            User.createNew(id, firstName, null, email);
        } catch (IllegalArgumentException e) {
            assertEquals("Last name cannot be null", e.getMessage());
            return;
        }

        fail();
    }

    @Test
    @DisplayName("Given a valid input when update profile then profile is updated")
    public void givenValidInput_whenUpdateProfile_thenUserIsUpdated() {
        User user = User.createNew(id, firstName, lastName, email);

        FirstName newFirstName = new FirstName("Juan");
        LastName newLastName = new LastName("Pérez");

        user.updateProfile(newFirstName, newLastName);

        assertEquals(newFirstName, user.getFirstName());
        assertEquals(newLastName, user.getLastName());
        assertEquals(email, user.getEmail());
    }

    @Test
    @DisplayName("Given a null first name when update profile then IllegalArgumentException is thrown")
    public void givenNullFirstName_whenUpdateProfile_thenIllegalArgumentExceptionIsThrown() {
        User user = User.createNew(id, firstName, lastName, email);

        try {
            user.updateProfile(null, lastName);
        } catch (IllegalArgumentException e) {
            assertEquals("First name cannot be null", e.getMessage());
            return;
        }

        fail();
    }

    @Test
    @DisplayName("Given a valid input when restore then user is restored")
    public void givenValidInput_whenRestore_thenUserIsRestored() {
        User user = User.restore(id, firstName, lastName, email);

        assertEquals(id, user.getId());
        assertEquals(firstName, user.getFirstName());
        assertEquals(lastName, user.getLastName());
        assertEquals(email, user.getEmail());
    }

    @Test
    @DisplayName("Given a null user id when restore then IllegalArgumentException is thrown")
    public void givenNullUserId_whenRestore_thenIllegalArgumentExceptionIsThrown() {
        try {
            User.restore(null, firstName, lastName, email);
        } catch (IllegalArgumentException e) {
            assertEquals("User id cannot be null", e.getMessage());
            return;
        }

        fail();
    }

}