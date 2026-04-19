package com.bsn.api.adapters.input.rest.mapper;

import com.bsn.api.core.port.input.command.SaveBookCommand;
import com.bsn.api.adapters.input.rest.dto.BookRequest;
import org.springframework.security.core.Authentication;

public class BookMapper {

    public static SaveBookCommand toSaveBookCommand(BookRequest bookRequest, Authentication connectedUser) {
        return new SaveBookCommand(bookRequest.id(), bookRequest.title(), bookRequest.authorName(), bookRequest.isbn(),
                bookRequest.synopsis(), bookRequest.shareable(), connectedUser.getName());
    }

}
