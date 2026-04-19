package com.bsn.api.core.service;

import com.bsn.api.core.entity.User;
import com.bsn.api.core.port.input.command.SaveUserCommand;
import com.bsn.api.core.port.output.LoggingPort;
import com.bsn.api.core.port.output.UserRepositoryPort;
import com.bsn.api.core.value.Email;
import com.bsn.api.core.value.FirstName;
import com.bsn.api.core.value.LastName;
import com.bsn.api.core.value.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class SaveUserServiceTest {

    private final UserRepositoryPort userRepositoryPort = mock(UserRepositoryPort.class);

    private final LoggingPort loggingPort = mock(LoggingPort.class);

    private final SaveUserService saveUserService = new SaveUserService(userRepositoryPort, loggingPort);


    @Test
    @DisplayName("Given a non existing user when save then user is created")
    public void givenNonExistingUser_whenSave_thenUserIsCreated() {
        SaveUserCommand command = new SaveUserCommand("abc-123", "John", "Doe",
                "john@mail.com");

        when(userRepositoryPort.findById("abc-123")).thenReturn(Optional.empty());
        when(userRepositoryPort.create(any(User.class))).thenAnswer(i -> i.getArgument(0));

        User createdUser = saveUserService.save(command);

        assertEquals(new UserId("abc-123"), createdUser.getId());
        assertEquals(new FirstName("John"), createdUser.getFirstName());
        assertEquals(new LastName("Doe"), createdUser.getLastName());
        assertEquals(new Email("john@mail.com"), createdUser.getEmail());

        verify(userRepositoryPort, never()).update(any(User.class));
        verify(loggingPort, times(2)).info(anyString());
    }

    @Test
    @DisplayName("Given an existing user when save then user is updated")
    public void givenExistingUser_whenSave_thenUserIsUpdated() {
        SaveUserCommand command = new SaveUserCommand("abc-123", "Juan", "Pérez",
                "john@mail.com");

        User existingUser = User.restore(new UserId("abc-123"), new FirstName("John"),
                new LastName("Doe"), new Email("john@mail.com"));

        when(userRepositoryPort.findById("abc-123")).thenReturn(Optional.of(existingUser));
        when(userRepositoryPort.update(any(User.class))).thenAnswer(i -> i.getArgument(0));

        User updatedUser = saveUserService.save(command);

        assertEquals(new FirstName("Juan"), updatedUser.getFirstName());
        assertEquals(new LastName("Pérez"), updatedUser.getLastName());

        verify(userRepositoryPort, never()).create(any(User.class));
        verify(loggingPort, times(2)).info(anyString());
    }

}