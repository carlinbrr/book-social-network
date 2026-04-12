package com.bsn.api.core.port.output;

import com.bsn.api.core.entity.Book;

import java.util.Optional;

public interface BookRepositoryPort {

    Optional<Book> findById(Integer id);

    Integer create(Book book);

    Integer update(Book book);
}
