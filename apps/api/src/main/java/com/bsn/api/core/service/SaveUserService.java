package com.bsn.api.core.service;

import com.bsn.api.core.entity.User;
import com.bsn.api.core.value.Email;
import com.bsn.api.core.value.FirstName;
import com.bsn.api.core.value.LastName;
import com.bsn.api.core.value.UserId;
import com.bsn.api.core.port.input.SaveUserUseCase;
import com.bsn.api.core.port.input.command.SaveUserCommand;
import com.bsn.api.core.port.output.LoggingPort;
import com.bsn.api.core.port.output.UserRepositoryPort;

import java.util.Optional;

public class SaveUserService implements SaveUserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    private final LoggingPort loggingPort;


    public SaveUserService(UserRepositoryPort userRepositoryPort, LoggingPort loggingPort) {
        this.userRepositoryPort = userRepositoryPort;
        this.loggingPort = loggingPort;
    }

    @Override
    public User save(SaveUserCommand command) {
        Optional<User> userOptional = userRepositoryPort.findById(command.id());

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            loggingPort.info("Updating user: " + user);
            user.updateProfile(new FirstName(command.firstName()), new LastName(command.lastName()));
            User updatedUser = userRepositoryPort.update(user);
            loggingPort.info("User successfully updated: " + updatedUser);
            return updatedUser;
        }

        User user = User.createNew(new UserId(command.id()), new FirstName(command.firstName()), new LastName(command.lastName()),
                new Email(command.email()));
        loggingPort.info("Creating user: " + user);
        User createdUser = userRepositoryPort.create(user);
        loggingPort.info("User successfully created");
        return createdUser;
    }

}
