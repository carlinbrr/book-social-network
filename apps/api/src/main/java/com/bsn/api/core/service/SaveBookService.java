package com.bsn.api.core.service;

import com.bsn.api.core.entity.Book;
import com.bsn.api.core.exception.BookNotFoundException;
import com.bsn.api.core.exception.UserNotFoundException;
import com.bsn.api.core.value.AuthorName;
import com.bsn.api.core.value.Isbn;
import com.bsn.api.core.value.Synopsis;
import com.bsn.api.core.value.Title;
import com.bsn.api.core.entity.User;
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
    public Book save(SaveBookCommand command) {
        User user = userRepositoryPort.findById(command.ownerId()).orElseThrow( () ->
                new UserNotFoundException("User not found with id: " + command.ownerId()));

        if (command.id() != null) {
            Book book = bookRepositoryPort.findById(command.id()).orElseThrow( () ->
                    new BookNotFoundException("Book not found with id: " + command.id()));

            loggingPort.info("Updating book: " + book);
            book.updateDetails(new Title(command.title()), new AuthorName(command.authorName()), new Isbn(command.isbn()),
                    new Synopsis(command.synopsis()), command.shareable(), user.getId());
            Book updatedBook =  bookRepositoryPort.update(book);
            loggingPort.info("Book successfully updated: " + updatedBook);
            return updatedBook;
        }

        Book book = Book.createNew(new Title(command.title()), new AuthorName(command.authorName()), new Isbn(command.isbn()),
                new Synopsis(command.synopsis()), command.shareable(), user.getId());
        loggingPort.info("Creating book: " + book);
        Book createdBook = bookRepositoryPort.create(book);
        loggingPort.info("Book successfully created: " + createdBook);
        return createdBook;
    }

}
