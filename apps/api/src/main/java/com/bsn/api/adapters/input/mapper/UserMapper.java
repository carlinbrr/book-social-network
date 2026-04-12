package com.bsn.api.adapters.input.mapper;

import com.bsn.api.adapters.input.dto.UserRequest;
import com.bsn.api.core.port.input.command.SaveUserCommand;

public class UserMapper {

    public static SaveUserCommand toSaveUserCommand(UserRequest userRequest) {
        return new SaveUserCommand(userRequest.keycloakId(), userRequest.firstName(), userRequest.lastName(),
                userRequest.email());
    }

}
