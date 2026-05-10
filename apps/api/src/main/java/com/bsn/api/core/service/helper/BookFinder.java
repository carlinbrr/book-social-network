package com.bsn.api.core.service.helper;

import com.bsn.api.core.entity.Book;
import com.bsn.api.core.exception.BookNotFoundException;
import com.bsn.api.core.port.output.BookRepositoryPort;
import com.bsn.api.core.value.BookId;

public class BookFinder {

    private final BookRepositoryPort bookRepositoryPort;


    public BookFinder(BookRepositoryPort bookRepositoryPort) {
        this.bookRepositoryPort = bookRepositoryPort;
    }

    public Book findExisting(BookId id) {
        return bookRepositoryPort.findById(id).orElseThrow(
                () -> new BookNotFoundException("Book not found with id: " + id.getValue()));
    }

}
