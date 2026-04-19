package com.bsn.api.core.port.input;

import com.bsn.api.core.entity.Book;
import com.bsn.api.core.port.input.command.SaveBookCommand;

public interface SaveBookUseCase {

    Book save(SaveBookCommand command);

}
