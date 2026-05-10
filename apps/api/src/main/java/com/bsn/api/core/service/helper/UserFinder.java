package com.bsn.api.core.service.helper;

import com.bsn.api.core.entity.User;
import com.bsn.api.core.exception.UserNotFoundException;
import com.bsn.api.core.port.output.UserRepositoryPort;
import com.bsn.api.core.value.UserId;

public class UserFinder {

    private final UserRepositoryPort userRepositoryPort;


    public UserFinder(UserRepositoryPort userRepositoryPort) {
        this.userRepositoryPort = userRepositoryPort;
    }

    public User findExisting(UserId id) {
        // TODO: Add logging
        return userRepositoryPort.findById(id).orElseThrow(
                () -> new UserNotFoundException("User not found with id: " + id.getValue()));
    }

}
