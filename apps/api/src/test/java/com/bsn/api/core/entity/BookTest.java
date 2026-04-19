package com.bsn.api.core.entity;

import com.bsn.api.core.value.AuthorName;
import com.bsn.api.core.value.BookCover;
import com.bsn.api.core.value.BookId;
import com.bsn.api.core.value.Isbn;
import com.bsn.api.core.value.Synopsis;
import com.bsn.api.core.value.Title;
import com.bsn.api.core.value.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    private final Title title = new Title("Clean Code");

    private final AuthorName authorName = new AuthorName("Robert C. Martin");

    private final Isbn isbn = new Isbn("1234567890");

    private final Synopsis synopsis = new Synopsis("Synopsis...");

    private final UserId ownerId = new UserId("abc-123");


    @Test
    @DisplayName("Given a valid input when create new then book is created")
    public void givenValidInput_whenCreateNew_thenBookIsCreated() {
        Book book = Book.createNew(title, authorName, isbn, synopsis, true, ownerId);

        assertNull(book.getId());
        assertEquals(title, book.getTitle());
        assertEquals(authorName, book.getAuthorName());
        assertEquals(isbn, book.getIsbn());
        assertEquals(synopsis, book.getSynopsis());
        assertNull(book.getBookCover());
        assertFalse(book.isArchived());
        assertTrue(book.isShareable());
        assertEquals(ownerId, book.getOwnerId());
    }

    @Test
    @DisplayName("Given a valid input when restore then book is restored")
    public void givenValidInput_whenRestore_thenBookIsRestored() {
        BookId id = new BookId(10);
        BookCover cover = new BookCover("/temp/img/profile.png");

        Book book = Book.restore(id, title, authorName, isbn, synopsis, cover, true, false, ownerId);

        assertEquals(id, book.getId());
        assertEquals(title, book.getTitle());
        assertEquals(authorName, book.getAuthorName());
        assertEquals(isbn, book.getIsbn());
        assertEquals(synopsis, book.getSynopsis());
        assertEquals(cover, book.getBookCover());
        assertTrue(book.isArchived());
        assertFalse(book.isShareable());
        assertEquals(ownerId, book.getOwnerId());
    }

    @Test
    @DisplayName("Given a null title when create new then IllegalArgumentException is thrown")
    public void givenNullTitle_whenCreateNew_thenIllegalArgumentExceptionIsThrown() {
        try {
            Book.createNew(null, authorName, isbn, synopsis, true, ownerId);
        } catch (IllegalArgumentException e) {
            assertEquals("Title cannot be null", e.getMessage());
            return;
        }

        fail("IllegalArgumentException should have been thrown");
    }

    @Test
    @DisplayName("Given a null author name when create new then IllegalArgumentException is thrown")
    public void givenNullAuthorName_whenCreateNew_thenIllegalArgumentExceptionIsThrown() {
        try {
            Book.createNew(title, null, isbn, synopsis, true, ownerId);
        } catch (IllegalArgumentException e) {
            assertEquals("Author name cannot be null", e.getMessage());
            return;
        }

        fail("IllegalArgumentException should have been thrown");
    }

    @Test
    @DisplayName("Given a null isbn when create new then IllegalArgumentException is thrown")
    public void givenNullIsbn_whenCreateNew_thenIllegalArgumentExceptionIsThrown() {
        try {
            Book.createNew(title, authorName, null, synopsis, true, ownerId);
        } catch (IllegalArgumentException e) {
            assertEquals("Isbn cannot be null", e.getMessage());
            return;
        }

        fail("IllegalArgumentException should have been thrown");
    }

    @Test
    @DisplayName("Given a null synopsis when create new then IllegalArgumentException is thrown")
    public void givenNullSynopsis_whenCreateNew_thenIllegalArgumentExceptionIsThrown() {
        try {
            Book.createNew(title, authorName, isbn, null, true, ownerId);
        } catch (IllegalArgumentException e) {
            assertEquals("Synopsis cannot be null", e.getMessage());
            return;
        }

        fail("IllegalArgumentException should have been thrown");
    }

    @Test
    @DisplayName("Given a null owner id when create new then IllegalArgumentException is thrown")
    public void givenNullOwnerId_whenCreateNew_thenIllegalArgumentExceptionIsThrown() {
        try {
            Book.createNew(title, authorName, isbn, synopsis, true, null);
        } catch (IllegalArgumentException e) {
            assertEquals("Owner id cannot be null", e.getMessage());
            return;
        }

        fail("IllegalArgumentException should have been thrown");
    }

    @Test
    @DisplayName("Given a null id when restore then IllegalArgumentException is thrown")
    public void givenNullId_whenRestore_thenIllegalArgumentExceptionIsThrown() {
        try {
            Book.restore(null, title, authorName, isbn, synopsis, null, false, true, ownerId);
        } catch (IllegalArgumentException e) {
            assertEquals("Book id cannot be null", e.getMessage());
            return;
        }

        fail("IllegalArgumentException should have been thrown");
    }

    @Test
    @DisplayName("Given an archived and shareable book when restore then IllegalStateException is thrown")
    public void givenArchivedAndShareableBook_whenRestore_thenIllegalStateExceptionIsThrown() {
        try {
            Book.restore(new BookId(10), title, authorName, isbn, synopsis, null, true, true, ownerId);
        } catch (IllegalStateException e) {
            assertEquals("Archived book cannot be shareable", e.getMessage());
            return;
        }

        fail("IllegalStateException should have been thrown");
    }

    @Test
    @DisplayName("Given a valid input when update details then book is updated")
    public void givenValidInput_whenUpdateDetails_thenBookIsUpdated() {
        Book book = Book.createNew(title, authorName, isbn, synopsis, false, ownerId);
        Title newTitle = new Title("Effective Java");
        AuthorName newAuthorName = new AuthorName("Joshua Bloch");
        Isbn newIsbn = new Isbn("0987654321");
        Synopsis newSynopsis = new Synopsis("Another synopsis...");

        book.updateDetails(newTitle, newAuthorName, newIsbn, newSynopsis, true, ownerId);

        assertEquals(newTitle, book.getTitle());
        assertEquals(newAuthorName, book.getAuthorName());
        assertEquals(newIsbn, book.getIsbn());
        assertEquals(newSynopsis, book.getSynopsis());
        assertTrue(book.isShareable());
    }

    @Test
    @DisplayName("Given a valid input when update details with different user than owner then IllegalStateException is thrown")
    public void givenValidInput_whenUpdateDetailsWithDifferentUserThanOwner_thenIllegalStateExceptionIsThrown() {
        Book book = Book.createNew(title, authorName, isbn, synopsis, false, ownerId);
        UserId user = new UserId("321-cba");

        try {
            book.updateDetails(title, authorName, isbn, synopsis, false, user);
        } catch (IllegalStateException e) {
            assertEquals("Only book owner can update details", e.getMessage());
            return;
        }

        fail("IllegalStateException should have been thrown");
    }

    @Test
    @DisplayName("Given an archived book and a true shareable status when update details then book is unarchived")
    public void givenArchivedBookAndTrueShareableStatus_whenUpdateDetails_thenBookIsUnarchived() {
        Book book = Book.restore(new BookId(10), title, authorName, isbn, synopsis, null,
                true, false, ownerId);

        book.updateDetails(title, authorName, isbn, synopsis, true, ownerId);

        assertTrue(book.isShareable());
        assertFalse(book.isArchived());
    }

    @Test
    @DisplayName("Given a null title when update details then IllegalArgumentException is thrown")
    public void givenNullTitle_whenUpdateDetails_thenIllegalArgumentExceptionIsThrown() {
        Book book = Book.createNew(title, authorName, isbn, synopsis, false, ownerId);

        try {
            book.updateDetails(null, authorName, isbn, synopsis, false, ownerId);
        } catch (IllegalArgumentException e) {
            assertEquals("Title cannot be null", e.getMessage());
            return;
        }

        fail("IllegalArgumentException should have been thrown");
    }

}