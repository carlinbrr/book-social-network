package com.bsn.api.adapters.output.presitence;

import com.bsn.api.adapters.output.presitence.mapper.BookQueryMapper;
import com.bsn.api.adapters.output.presitence.repository.JpaBookQueryRepository;
import com.bsn.api.core.model.BookDetailsData;
import com.bsn.api.core.port.output.BookQueryPort;
import com.bsn.api.core.value.BookId;
import com.bsn.api.core.value.UserId;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BookQueryAdapter implements BookQueryPort {

    private final JpaBookQueryRepository jpaBookQueryRepository;


    public BookQueryAdapter(JpaBookQueryRepository jpaBookQueryRepository) {
        this.jpaBookQueryRepository = jpaBookQueryRepository;
    }

    @Override
    public Optional<BookDetailsData> findDetailsById(BookId bookId, UserId userId) {
        return jpaBookQueryRepository.findDetailsById(bookId.getValue(), userId.getValue()).map(BookQueryMapper::toBookDetailsData);
    }

}
