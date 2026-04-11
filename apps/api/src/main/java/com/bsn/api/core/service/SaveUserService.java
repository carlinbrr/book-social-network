package com.bsn.api.core.service;

import com.bsn.api.core.entity.User;
import com.bsn.api.core.port.input.SaveUserUseCase;
import com.bsn.api.core.port.input.command.SaveUserCommand;
import com.bsn.api.core.port.output.LoggingPort;
import com.bsn.api.core.port.output.UserRepositoryPort;

public class SaveUserService implements SaveUserUseCase {

    private final UserRepositoryPort userRepositoryPort;

    private final LoggingPort loggingPort;


    public SaveUserService(UserRepositoryPort userRepositoryPort, LoggingPort loggingPort) {
        this.userRepositoryPort = userRepositoryPort;
        this.loggingPort = loggingPort;
    }

    @Override
    public void save(SaveUserCommand command) {
        User user = userRepositoryPort.findById(command.id());

        if ( user != null ) {
            user.updateProfile(command.firstName(), command.lastName(), command.email());
            loggingPort.info("Updating user: " + user);
            userRepositoryPort.save(user);
            loggingPort.info("User successfully updated");
            return;
        }

        user = new User(command.id(), command.firstName(), command.lastName(), command.email());
        loggingPort.info("Saving user: " + user);
        userRepositoryPort.save(user);
        loggingPort.info("User successfully saved");
    }

}
