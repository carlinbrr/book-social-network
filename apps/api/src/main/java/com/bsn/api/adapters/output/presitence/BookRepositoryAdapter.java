package com.bsn.api.adapters.output.presitence;

import com.bsn.api.adapters.output.presitence.entity.Book;
import com.bsn.api.adapters.output.presitence.entity.User;
import com.bsn.api.adapters.output.presitence.mapper.BookMapper;
import com.bsn.api.adapters.output.presitence.repository.JpaBookRepository;
import com.bsn.api.adapters.output.presitence.repository.JpaUserRepository;
import com.bsn.api.core.port.output.BookRepositoryPort;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class BookRepositoryAdapter implements BookRepositoryPort {

    private final JpaBookRepository jpaBookRepository;

    private final JpaUserRepository jpaUserRepository;


    public BookRepositoryAdapter(JpaBookRepository jpaBookRepository,  JpaUserRepository jpaUserRepository) {
        this.jpaBookRepository = jpaBookRepository;
        this.jpaUserRepository = jpaUserRepository;
    }

    @Override
    public Optional<com.bsn.api.core.entity.Book> findById(Integer id) {
        Optional<Book> jpaBookOptional =  jpaBookRepository.findById(id);
        return jpaBookOptional.map(BookMapper::toBook);
    }

    @Override
    public Integer create(com.bsn.api.core.entity.Book book) {
        User jpaUser = jpaUserRepository.findByKeycloakId(book.getOwnerId()).orElseThrow( () ->
                new IllegalStateException("User not found with id " + book.getOwnerId()));

        return jpaBookRepository.save(BookMapper.toJpaBook(book, jpaUser)).getId();
    }

    @Override
    public Integer update(com.bsn.api.core.entity.Book book) {
        Book jpaBook = jpaBookRepository.findById(book.getId()).orElseThrow( () ->
                new IllegalStateException("Book not found with id " + book.getId()));

        BookMapper.mergeBook(jpaBook, book);
        return jpaBookRepository.save(jpaBook).getId();
    }

}
