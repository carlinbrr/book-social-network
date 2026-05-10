package com.bsn.api.core.service;

import com.bsn.api.core.entity.Book;
import com.bsn.api.core.entity.User;
import com.bsn.api.core.port.input.command.SaveBookCommand;
import com.bsn.api.core.port.output.BookRepositoryPort;
import com.bsn.api.core.port.output.LoggingPort;
import com.bsn.api.core.service.helper.BookFinder;
import com.bsn.api.core.service.helper.UserFinder;
import com.bsn.api.core.value.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookManagementServiceTest {

    private final UserFinder userFinder = mock(UserFinder.class);

    private final BookFinder bookFinder = mock(BookFinder.class);

    private final BookRepositoryPort bookRepositoryPort = mock(BookRepositoryPort.class);

    private final LoggingPort loggingPort = mock(LoggingPort.class);

    private final BookManagementService saveBookService = new BookManagementService(userFinder, bookFinder, bookRepositoryPort,
            loggingPort);


    @Test
    @DisplayName("Given a non existing book and existing user when save then book is created")
    public void givenNonExistingBookAndExistingUser_whenSave_thenBookIsCreated() {
        SaveBookCommand command = new SaveBookCommand(null, "Clean Code", "Robert C. Martin",
                "1234567890", "Synopsis...", true, "abc-123");

        User user = User.restore(new UserId("abc-123"), new FirstName("John"), new LastName("Doe"),
                new Email("john@mail.com"));

        when(userFinder.findExisting(new UserId("abc-123"))).thenReturn(user);
        when(bookRepositoryPort.create(any(Book.class))).thenAnswer(i ->  {
            Book inputBook = i.getArgument(0);
            return Book.restore(new BookId(10), inputBook.getTitle(), inputBook.getAuthorName(), inputBook.getIsbn(),
                    inputBook.getSynopsis(), inputBook.getBookCover(), inputBook.isArchived(), inputBook.isShareable(),
                    inputBook.getOwnerId());
        });

        Book createdBook = saveBookService.save(command);

        assertEquals(new BookId(10), createdBook.getId());
        assertEquals(new Title("Clean Code"), createdBook.getTitle());
        assertEquals(new AuthorName("Robert C. Martin"), createdBook.getAuthorName());
        assertEquals(new Isbn("1234567890"), createdBook.getIsbn());
        assertEquals(new Synopsis("Synopsis..."), createdBook.getSynopsis());
        assertNull(createdBook.getBookCover());
        assertFalse(createdBook.isArchived());
        assertTrue(createdBook.isShareable());
        assertEquals(new UserId("abc-123"), createdBook.getOwnerId());
    }

    @Test
    @DisplayName("Given a valid existing book and an existing user when save then book is updated")
    public void givenValidExistingBook_whenSave_thenBookIsUpdated() {
        SaveBookCommand command = new SaveBookCommand(10, "Clean Architecture", "Robert C. Martin",
                "0987654321", "Another Synopsis...", false, "abc-123");

        User user = User.restore(new UserId("abc-123"), new FirstName("John"), new LastName("Doe"),
                new Email("john@mail.com"));

        Book existingBook = Book.restore(new BookId(10), new Title("Clean Code"),
                new AuthorName("Robert C. Martin"), new Isbn("1234567890"), new Synopsis("Synopsis..."),
                null, false, true, user.getId());

        when(userFinder.findExisting(new UserId("abc-123"))).thenReturn(user);
        when(bookFinder.findExisting(new BookId(10))).thenReturn(existingBook);
        when(bookRepositoryPort.update(any(Book.class))).thenAnswer(i -> i.getArgument(0));

        Book updatedBook = saveBookService.save(command);

        assertEquals(new BookId(10), updatedBook.getId());
        assertEquals(new Title("Clean Architecture"), updatedBook.getTitle());
        assertEquals(new AuthorName("Robert C. Martin"), updatedBook.getAuthorName());
        assertEquals(new Isbn("0987654321"), updatedBook.getIsbn());
        assertEquals(new Synopsis("Another Synopsis..."), updatedBook.getSynopsis());
        assertNull(updatedBook.getBookCover());
        assertFalse(updatedBook.isArchived());
        assertFalse(updatedBook.isShareable());
        assertEquals(new UserId("abc-123"), updatedBook.getOwnerId());
    }

}
