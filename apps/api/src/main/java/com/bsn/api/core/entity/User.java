package com.bsn.api.core.entity;

import com.bsn.api.core.exception.EmailCannotChangeException;

public class User {

    private String id;

    private String firstName;

    private String lastName;

    private String email;


    public String getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }


    public User(String id, String firstName, String lastName, String email) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public void updateProfile(String firstName, String lastName, String email) {
        if ( !this.email.equals(email) ) {
            throw new EmailCannotChangeException("The email can't be changed once it's set");
        }

        this.firstName = firstName;
        this.lastName = lastName;
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
