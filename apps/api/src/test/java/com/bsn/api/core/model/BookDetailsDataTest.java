package com.bsn.api.core.model;

import com.bsn.api.core.value.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookDetailsDataTest {

    private final BookId id = new BookId(1);

    private final Title title = new Title("Clean Code");

    private final AuthorName authorName = new AuthorName("Robert C. Martin");

    private final Isbn isbn = new Isbn("1234567890");

    private final Synopsis synopsis = new Synopsis("A must-read book...");

    private final BookCover bookCover = new BookCover("/images/cover.jpg");

    private final FirstName ownerFirstName = new FirstName("John");

    private final LastName ownerLastName = new LastName("Doe");


    @Test
    @DisplayName("Given a valid input when new then book details data is created")
    public void givenValidInput_whenNew_thenBookDetailsDataIsCreated() {
        BookDetailsData data = new BookDetailsData(id, title, authorName, isbn, synopsis,
                bookCover, false, true, ownerFirstName, ownerLastName, 4.5, false);

        assertEquals(id, data.id());
        assertEquals(title, data.title());
        assertEquals(authorName, data.authorName());
        assertEquals(isbn, data.isbn());
        assertEquals(synopsis, data.synopsis());
        assertEquals(bookCover, data.bookCover());
        assertFalse(data.archived());
        assertTrue(data.shareable());
        assertEquals(ownerFirstName, data.ownerFirstName());
        assertEquals(ownerLastName, data.ownerLastName());
        assertEquals(4.5, data.averageRating());
        assertFalse(data.isInWaitingList());
    }

    @Test
    @DisplayName("Given a null id when new then IllegalArgumentException is thrown")
    public void givenNullId_whenNew_thenIllegalArgumentExceptionIsThrown() {
        try {
            new BookDetailsData(null, title, authorName, isbn, synopsis, bookCover, false, true,
                    ownerFirstName, ownerLastName, 4.5, true);
        } catch (IllegalArgumentException e) {
            assertEquals("Id cannot be null", e.getMessage());
            return;
        }

        fail("IllegalArgumentException should have been thrown");
    }

    @Test
    @DisplayName("Given a null title when new then IllegalArgumentException is thrown")
    public void givenNullTitle_whenNew_thenIllegalArgumentExceptionIsThrown() {
        try {
            new BookDetailsData(id, null, authorName, isbn, synopsis, bookCover, false, true,
                    ownerFirstName, ownerLastName, 4.5, false);
        } catch (IllegalArgumentException e) {
            assertEquals("Title cannot be null", e.getMessage());
            return;
        }

        fail("IllegalArgumentException should have been thrown");
    }

    @Test
    @DisplayName("Given a null author name when new then IllegalArgumentException is thrown")
    public void givenNullAuthorName_whenNew_thenIllegalArgumentExceptionIsThrown() {
        try {
            new BookDetailsData(id, title, null, isbn, synopsis, bookCover, false, true,
                    ownerFirstName, ownerLastName, 4.5, true);
        } catch (IllegalArgumentException e) {
            assertEquals("Author name cannot be null", e.getMessage());
            return;
        }

        fail("IllegalArgumentException should have been thrown");
    }

    @Test
    @DisplayName("Given a null isbn when new then IllegalArgumentException is thrown")
    public void givenNullIsbn_whenNew_thenIllegalArgumentExceptionIsThrown() {
        try {
            new BookDetailsData(id, title, authorName, null, synopsis, bookCover, false, true,
                    ownerFirstName, ownerLastName, 4.5, false);
        } catch (IllegalArgumentException e) {
            assertEquals("Isbn cannot be null", e.getMessage());
            return;
        }

        fail("IllegalArgumentException should have been thrown");
    }

    @Test
    @DisplayName("Given a null synopsis when new then IllegalArgumentException is thrown")
    public void givenNullSynopsis_whenNew_thenIllegalArgumentExceptionIsThrown() {
        try {
            new BookDetailsData(id, title, authorName, isbn, null, bookCover, false, true,
                    ownerFirstName, ownerLastName, 4.5, true);
        } catch (IllegalArgumentException e) {
            assertEquals("Synopsis cannot be null", e.getMessage());
            return;
        }

        fail("IllegalArgumentException should have been thrown");
    }

    @Test
    @DisplayName("Given a null owner first name when new then IllegalArgumentException is thrown")
    public void givenNullOwnerFirstName_whenNew_thenIllegalArgumentExceptionIsThrown() {
        try {
            new BookDetailsData(id, title, authorName, isbn, synopsis, bookCover, false, true,
                    null, ownerLastName, 4.5, false);
        } catch (IllegalArgumentException e) {
            assertEquals("Owner's first name cannot be null", e.getMessage());
            return;
        }

        fail("IllegalArgumentException should have been thrown");
    }

    @Test
    @DisplayName("Given a null owner last name when new then IllegalArgumentException is thrown")
    public void givenNullOwnerLastName_whenNew_thenIllegalArgumentExceptionIsThrown() {
        try {
            new BookDetailsData(id, title, authorName, isbn, synopsis, bookCover, false, true,
                    ownerFirstName, null, 4.5, true);
        } catch (IllegalArgumentException e) {
            assertEquals("Owner's last name cannot be null", e.getMessage());
            return;
        }

        fail("IllegalArgumentException should have been thrown");
    }

    @Test
    @DisplayName("Given an archived and shareable book when new then IllegalStateException is thrown")
    public void givenArchivedAndShareableBook_whenNew_thenIllegalStateExceptionIsThrown() {
        try {
            new BookDetailsData(id, title, authorName, isbn, synopsis, bookCover, true, true,
                    ownerFirstName, ownerLastName, 4.5, false);
        } catch (IllegalArgumentException e) {
            assertEquals("Archived book cannot be shareable", e.getMessage());
            return;
        }

        fail("IllegalArgumentException should have been thrown");
    }

    @Test
    @DisplayName("Given valid owner names when get owner full name then returns concatenated names")
    public void givenValidOwnerNames_whenGetOwnerFullName_thenReturnsConcatenatedNames() {
        BookDetailsData data = new BookDetailsData(id, title, authorName, isbn, synopsis,
                bookCover, false, true, ownerFirstName, ownerLastName, 4.5, true);

        String fullName = data.getOwnerFullName();

        assertEquals("John Doe", fullName);
    }

    @Test
    @DisplayName("Given a book with null average rating when new then book details data is created")
    public void givenNullAverageRating_whenNew_thenBookDetailsDataIsCreated() {
        BookDetailsData data = new BookDetailsData(id, title, authorName, isbn, synopsis,
                bookCover, false, true, ownerFirstName, ownerLastName, null, false);

        assertNull(data.averageRating());
    }

    @Test
    @DisplayName("Given archived false and shareable false when new then book details data is created")
    public void givenArchivedFalseAndShareableFalse_whenNew_thenBookDetailsDataIsCreated() {
        BookDetailsData data = new BookDetailsData(id, title, authorName, isbn, synopsis,
                bookCover, false, false, ownerFirstName, ownerLastName, 3.0, true);

        assertFalse(data.archived());
        assertFalse(data.shareable());
    }

    @Test
    @DisplayName("Given archived true and shareable false when new then book details data is created")
    public void givenArchivedTrueAndShareableFalse_whenNew_thenBookDetailsDataIsCreated() {
        BookDetailsData data = new BookDetailsData(id, title, authorName, isbn, synopsis,
                bookCover, true, false, ownerFirstName, ownerLastName, 2.5, false);

        assertTrue(data.archived());
        assertFalse(data.shareable());
    }

}