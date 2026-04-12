package com.bsn.api.core.service;

import com.bsn.api.core.entity.User;
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
    public void save(SaveUserCommand command) {
        Optional<User> userOptional = userRepositoryPort.findById(command.id());

        if ( userOptional.isPresent() ) {
            User user = userOptional.get();
            loggingPort.info("Updating user: " + user);
            user.updateProfile(command.firstName(), command.lastName(), command.email());
            userRepositoryPort.update(user);
            loggingPort.info("User successfully updated: " + user);
            return;
        }

        User user = new User(command.id(), command.firstName(), command.lastName(), command.email());
        loggingPort.info("Creating user: " + user);
        userRepositoryPort.create(user);
        loggingPort.info("User successfully created");
    }

}
