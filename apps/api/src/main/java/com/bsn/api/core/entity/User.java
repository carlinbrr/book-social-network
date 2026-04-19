package com.bsn.api.core.entity;

import com.bsn.api.core.value.Email;
import com.bsn.api.core.value.FirstName;
import com.bsn.api.core.value.LastName;
import com.bsn.api.core.value.UserId;

public class User {

    private final UserId id;

    private FirstName firstName;

    private LastName lastName;

    private final Email email;


    public UserId getId() {
        return id;
    }

    public FirstName getFirstName() {
        return firstName;
    }

    public LastName getLastName() {
        return lastName;
    }

    public Email getEmail() {
        return email;
    }


    private User(UserId id, FirstName firstName, LastName lastName, Email email) {
        if (id == null) {
            throw new IllegalArgumentException("User id cannot be null");
        }

        if (email == null) {
            throw new IllegalArgumentException("Email cannot be null");
        }

        validateRequired(firstName, lastName);

        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public static User createNew(UserId id, FirstName firstName, LastName lastName, Email email) {
        return new User(id, firstName, lastName, email);
    }

    public void updateProfile(FirstName firstName, LastName lastName) {
        validateRequired(firstName, lastName);

        this.firstName = firstName;
        this.lastName = lastName;
    }

    public static User restore(UserId id, FirstName firstName, LastName lastName, Email email) {
        return new User(id, firstName, lastName, email);
    }

    private static void validateRequired(FirstName firstName, LastName lastName) {
        if (firstName == null) {
            throw new IllegalArgumentException("First name cannot be null");
        }

        if (lastName == null) {
            throw new IllegalArgumentException("Last name cannot be null");
        }
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                '}';
    }

}
