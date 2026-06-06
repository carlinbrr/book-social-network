package com.bsn.api.adapters.output.presitence;

import com.bsn.api.core.exception.DataConsistencyException;
import com.bsn.api.adapters.output.presitence.mapper.BookQueryMapper;
import com.bsn.api.adapters.output.presitence.mapper.PageMapper;
import com.bsn.api.adapters.output.presitence.repository.JpaBookQueryRepository;
import com.bsn.api.core.model.*;
import com.bsn.api.core.port.output.BookQueryPort;
import com.bsn.api.core.value.BookId;
import com.bsn.api.core.value.UserId;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
        try {
            return jpaBookQueryRepository.findDetailsById(bookId.getValue(), userId.getValue()).map(BookQueryMapper::toBookDetailsData);
        } catch (IllegalArgumentException ex) {
            throw new DataConsistencyException(ex);
        }
    }

    @Override
    public Page<BookDetailsData> findDisplayableFor(PageCriteria pageCriteria, String searchTerm, UserId userId) {
        Pageable pageable = PageRequest.of(pageCriteria.pageNumber().value(), pageCriteria.pageSize().value(),
                resolveSort(pageCriteria.sortOrder()));

        try {
            return PageMapper.toPage(jpaBookQueryRepository.findDisplayableFor(pageable, searchTerm, userId.getValue()), BookQueryMapper::toBookDetailsData);
        } catch (IllegalArgumentException ex) {
            throw new DataConsistencyException(ex);
        }
    }

    private Sort resolveSort(SortOrder sortOrder) {
        return switch (sortOrder) {
            case NEWEST -> Sort.by("createdDate").descending();
        };
    }

}
