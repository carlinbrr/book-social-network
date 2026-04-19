package com.bsn.api.core.port.input;

import com.bsn.api.core.entity.User;
import com.bsn.api.core.port.input.command.SaveUserCommand;

public interface SaveUserUseCase {

    User save(SaveUserCommand command);

}
