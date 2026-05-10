package com.bsn.api.core.service.helper;

import com.bsn.api.core.entity.User;
import com.bsn.api.core.exception.UserNotFoundException;
import com.bsn.api.core.port.output.UserRepositoryPort;
import com.bsn.api.core.value.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserFinderTest {

    private final UserRepositoryPort userRepositoryPort = mock(UserRepositoryPort.class);

    private final UserFinder userFinder = new UserFinder(userRepositoryPort);


    @Test
    @DisplayName("Given an existing id when find existing then user is found")
    public void givenExistingId_whenFindExisting_thenUserIsFound() {
        UserId id = new UserId("123-abc");

        User user = User.restore(id, new FirstName("John"), new LastName("Doe"),
                new Email("john@mail.com"));

        when(userRepositoryPort.findById(id)).thenReturn(Optional.of(user));

        assertNotNull(userFinder.findExisting(id));
    }

    @Test
    @DisplayName("Given an non existing id when find existing then UserNotFoundException is thrown")
    public void givenNonExistingId_whenFindExisting_thenUserNotFoundExceptionIsThrown() {
        UserId id = new UserId("123-abc");

        when(userRepositoryPort.findById(id)).thenReturn(Optional.empty());

        try {
            userFinder.findExisting(id);
        } catch (UserNotFoundException e) {
            assertEquals("User not found with id: 123-abc", e.getMessage());
            return;
        }

        fail("UserNotFoundException should have been thrown");
    }

}
