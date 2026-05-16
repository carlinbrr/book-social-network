package com.bsn.api.adapters.output.presitence.repository;

import com.bsn.api.adapters.output.presitence.entity.Book;
import com.bsn.api.adapters.output.presitence.projection.BookDetailsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface JpaBookQueryRepository extends JpaRepository<Book, Integer> {
    @Query("""
            SELECT new com.bsn.api.adapters.output.presitence.projection.BookDetailsProjection (
                b.id,
                b.title,
                b.authorName,
                b.isbn,
                b.synopsis,
                b.bookCover,
                b.archived,
                b.shareable,
                b.owner.firstName,
                b.owner.lastName,
                (
                    SELECT COALESCE(AVG(f.note), 0)
                    FROM Feedback f
                    WHERE f.book.id = b.id
                )
            )
            FROM Book b
            WHERE b.id = :id
            """
    )
    Optional<BookDetailsProjection> findDetailsById(Integer id);

}
