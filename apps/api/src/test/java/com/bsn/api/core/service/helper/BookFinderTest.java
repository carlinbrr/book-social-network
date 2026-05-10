package com.bsn.api.core.service.helper;

import com.bsn.api.core.entity.Book;
import com.bsn.api.core.exception.BookNotFoundException;
import com.bsn.api.core.port.output.BookRepositoryPort;
import com.bsn.api.core.value.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookFinderTest {

    private final BookRepositoryPort bookRepositoryPort = mock(BookRepositoryPort.class);

    private final BookFinder bookFinder = new BookFinder(bookRepositoryPort);


    @Test
    @DisplayName("Given an existing id when find existing then book is found")
    public void givenExistingId_whenFindExisting_thenBookIsFound() {
        BookId id = new BookId(10);
        Book book = Book.restore(id, new Title("Clean Code"), new AuthorName("Robert C. Martin"),
                new Isbn("1234567890"), new Synopsis("Synopsis..."), null, false,
                true, new UserId("123-abc"));

        when(bookRepositoryPort.findById(id)).thenReturn(Optional.of(book));

        assertNotNull(bookFinder.findExisting(id));
    }

    @Test
    @DisplayName("Given an non existing id when find existing then BookNotFoundException is thrown")
    public void givenNonExistingId_whenFindExisting_thenBookNotFoundExceptionIsThrown() {
        BookId id = new BookId(10);

        when(bookRepositoryPort.findById(id)).thenReturn(Optional.empty());

        try {
            bookFinder.findExisting(id);
        } catch (BookNotFoundException e) {
            assertEquals("Book not found with id: " + 10, e.getMessage());
            return;
        }

        fail("BookNotFoundException should have been thrown");
    }

}
