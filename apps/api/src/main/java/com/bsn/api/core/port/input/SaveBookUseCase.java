package com.bsn.api.core.port.input;

import com.bsn.api.core.port.input.command.SaveBookCommand;

public interface SaveBookUseCase {

    Integer save(SaveBookCommand command);

}
