package com.bsn.api.core.service;

import com.bsn.api.core.entity.Book;
import com.bsn.api.core.service.helper.BookFinder;
import com.bsn.api.core.service.helper.UserFinder;
import com.bsn.api.core.value.*;
import com.bsn.api.core.entity.User;
import com.bsn.api.core.port.input.SaveBookUseCase;
import com.bsn.api.core.port.input.command.SaveBookCommand;
import com.bsn.api.core.port.output.BookRepositoryPort;
import com.bsn.api.core.port.output.LoggingPort;

public class BookManagementService implements SaveBookUseCase {

    private final UserFinder userFinder;

    private final BookFinder bookFinder;

    private final BookRepositoryPort bookRepositoryPort;

    private final LoggingPort loggingPort;


    public BookManagementService(UserFinder userFinder, BookFinder bookFinder, BookRepositoryPort bookRepositoryPort,
                           LoggingPort loggingPort) {
        this.userFinder = userFinder;
        this.bookFinder = bookFinder;
        this.bookRepositoryPort = bookRepositoryPort;
        this.loggingPort = loggingPort;
    }


    @Override
    public Book save(SaveBookCommand command) {
        User user = userFinder.findExisting(new UserId(command.ownerId()));

        if (command.id() != null) {
            Book book = bookFinder.findExisting(new BookId(command.id()));

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
