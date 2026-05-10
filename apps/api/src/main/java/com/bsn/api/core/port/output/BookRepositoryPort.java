package com.bsn.api.core.port.output;

import com.bsn.api.core.entity.Book;
import com.bsn.api.core.value.BookId;

import java.util.Optional;

public interface BookRepositoryPort {

    Optional<Book> findById(BookId id);

    Book create(Book book);

    Book update(Book book);
}
