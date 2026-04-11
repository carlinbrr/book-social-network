package com.bsn.api.core.port.input;

import com.bsn.api.core.port.input.command.SaveUserCommand;

public interface SaveUserUseCase {

    void save(SaveUserCommand command);

}
