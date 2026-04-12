package com.bsn.api.core.service;

import com.bsn.api.core.entity.Book;
import com.bsn.api.core.entity.User;
import com.bsn.api.core.exception.BookNotFoundException;
import com.bsn.api.core.exception.UserNotFoundException;
import com.bsn.api.core.port.input.SaveBookUseCase;
import com.bsn.api.core.port.input.command.SaveBookCommand;
import com.bsn.api.core.port.output.BookRepositoryPort;
import com.bsn.api.core.port.output.LoggingPort;
import com.bsn.api.core.port.output.UserRepositoryPort;

public class SaveBookService implements SaveBookUseCase {

    private final BookRepositoryPort bookRepositoryPort;

    private final UserRepositoryPort userRepositoryPort;

    private final LoggingPort loggingPort;


    public SaveBookService(BookRepositoryPort bookRepositoryPort, UserRepositoryPort userRepositoryPort,
                           LoggingPort loggingPort) {
        this.bookRepositoryPort = bookRepositoryPort;
        this.userRepositoryPort = userRepositoryPort;
        this.loggingPort = loggingPort;
    }


    @Override
    public Integer save(SaveBookCommand command) {
        User user = userRepositoryPort.findById(command.ownerId()).orElseThrow( () ->
                new UserNotFoundException(String.format("User not found with id: " + command.ownerId())));

        if ( command.id() != null ) {
            Book book = bookRepositoryPort.findById(command.id()).orElseThrow( () ->
                    new BookNotFoundException(String.format("Book not found with id: " + command.id())));

            loggingPort.info("Updating book: " + book);
            book.updateDetails(command.title(), command.authorName(), command.isbn(), command.synopsis(),
                    command.shareable(), user.getId());
            Integer bookId =  bookRepositoryPort.update(book);
            loggingPort.info("Book successfully updated: " + book);
            return bookId;
        }

        Book book = new Book(command.title(), command.authorName(), command.isbn(), command.synopsis(),
                command.shareable(), user.getId());
        loggingPort.info("Creating book: " + book);
        Integer bookId = bookRepositoryPort.create(book);
        loggingPort.info("Book successfully created with id: " + bookId);
        return bookId;
    }

}
