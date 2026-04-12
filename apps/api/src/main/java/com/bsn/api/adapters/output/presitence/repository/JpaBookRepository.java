package com.bsn.api.adapters.output.presitence.repository;

import com.bsn.api.adapters.output.presitence.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaBookRepository extends JpaRepository<Book, Integer> {
}
