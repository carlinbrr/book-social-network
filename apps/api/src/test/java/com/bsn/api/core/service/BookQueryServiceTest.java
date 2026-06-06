package com.bsn.api.core.service;

import com.bsn.api.core.exception.BookNotFoundException;
import com.bsn.api.core.model.*;
import com.bsn.api.core.port.output.BookQueryPort;
import com.bsn.api.core.port.output.ImageStoragePort;
import com.bsn.api.core.port.output.LoggingPort;
import com.bsn.api.core.value.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class BookQueryServiceTest {

    private final BookQueryPort bookQueryPort = mock(BookQueryPort.class);

    private final ImageStoragePort imageStoragePort = mock(ImageStoragePort.class);

    private final LoggingPort loggingPort = mock(LoggingPort.class);

    private final BookQueryService bookQueryService = new BookQueryService(bookQueryPort, imageStoragePort, loggingPort);


    @Test
    @DisplayName("Given an existing id when find book details then book is found")
    public void givenExistingId_whenFindBookDetails_thenBookIsFound() {
        BookId bookId = new BookId(10);
        UserId userId = new UserId("123-abc");
        String bookCoverUrl = "/temp/img/book.png";

        BookDetailsData bookDetailsData = new BookDetailsData(bookId, new Title("Clean Code"), new AuthorName("Robert C. Martin"),
                new Isbn("1234567890"), new Synopsis("Synopsis..."), new BookCover(bookCoverUrl), false,
                true, new FirstName("John"), new LastName("Doe"), 4.5, false);

        when(bookQueryPort.findDetailsById(bookId, userId)).thenReturn(Optional.of(bookDetailsData));
        when(imageStoragePort.resolveFromUrl(bookCoverUrl)).thenReturn(new byte[2048]);

        assertNotNull(bookQueryService.findBookDetails(10, "123-abc"));
    }

    @Test
    @DisplayName("Given an non existing id when find book details then BookNotFounException is thrown")
    public void givenNonExistingId_whenFindBookDetails_thenBookNotFoundExceptionIsThrown() {
        BookId bookId = new BookId(10);
        UserId userId = new UserId("123-abc");
        String bookCoverUrl = "/temp/img/book.png";

        when(bookQueryPort.findDetailsById(bookId, userId)).thenReturn(Optional.empty());
        when(imageStoragePort.resolveFromUrl(bookCoverUrl)).thenReturn(new byte[2048]);

        try {
            bookQueryService.findBookDetails(10, "123-abc");
        } catch (BookNotFoundException e) {
            assertEquals("Book not found with id: " + 10, e.getMessage());
            return;
        }

        fail("BookNotFoundException should have been thrown");
    }

    @Test
    @DisplayName("Given a valid search term and page criteria when find displayable books then returns page of book details")
    public void givenValidSearchTermAndPageCriteria_whenFindDisplayableBooks_thenReturnsPageOfBookDetails() {
        PageCriteria pageCriteria = new PageCriteria(new PageNumber(0), new PageSize(10), SortOrder.NEWEST);
        BookDetailsData bookDetailsData = new BookDetailsData(new BookId(1), new Title("Clean Code"), new AuthorName("Robert C. Martin"),
                new Isbn("1234567890"), new Synopsis("A must-read book..."), new BookCover("/images/cover.jpg"),
                false, true, new FirstName("John"), new LastName("Doe"), 4.5, false);
        Page<BookDetailsData> bookDetailsDataPage = new Page<>(
                List.of(bookDetailsData), 0, 10, 1, 1, true, true
        );

        when(bookQueryPort.findDisplayableFor(pageCriteria, "Clean", new UserId("user-123")))
                .thenReturn(bookDetailsDataPage);
        when(imageStoragePort.resolveFromUrl("/images/cover.jpg"))
                .thenReturn(new byte[]{1, 2, 3});

        Page<BookDetails> result = bookQueryService.findDisplayableBooks(pageCriteria, "Clean", new UserId("user-123"));

        assertNotNull(result);
        assertEquals(1, result.content().size());
        assertEquals(0, result.number());
        assertEquals(10, result.size());
        assertEquals(1, result.totalElements());
        assertTrue(result.first());
        assertTrue(result.last());
        verify(bookQueryPort).findDisplayableFor(pageCriteria, "Clean", new UserId("user-123"));
    }

    @Test
    @DisplayName("Given empty search term and page criteria when find displayable books then returns page of book details")
    public void givenEmptySearchTermAndPageCriteria_whenFindDisplayableBooks_thenReturnsPageOfBookDetails() {
        PageCriteria pageCriteria = new PageCriteria(new PageNumber(1), new PageSize(10), SortOrder.NEWEST);
        BookDetailsData bookDetailsData1 = new BookDetailsData(new BookId(1), new Title("Clean Code"), new AuthorName("Robert C. Martin"),
                new Isbn("1234567890"), new Synopsis("A must-read book..."), new BookCover("/images/cover.jpg"),
                false, true, new FirstName("John"), new LastName("Doe"), 4.5, false);
        BookDetailsData bookDetailsData2 = new BookDetailsData(new BookId(2), new Title("Effective Java"), new AuthorName("Joshua Bloch"),
                new Isbn("0987654321"), new Synopsis("Another book..."), new BookCover("/images/cover2.jpg"),
                false, true, new FirstName("Jane"), new LastName("Smith"), 4.0, false);
        Page<BookDetailsData> bookDetailsDataPage = new Page<>(
                List.of(bookDetailsData1, bookDetailsData2), 1, 10, 20, 2, false, false
        );

        when(bookQueryPort.findDisplayableFor(pageCriteria, "", new UserId("user-123")))
                .thenReturn(bookDetailsDataPage);
        when(imageStoragePort.resolveFromUrl(any()))
                .thenReturn(new byte[]{1, 2, 3});

        Page<BookDetails> result = bookQueryService.findDisplayableBooks(pageCriteria, "", new UserId("user-123"));

        assertNotNull(result);
        assertEquals(2, result.content().size());
        assertEquals(1, result.number());
        assertEquals(20, result.totalElements());
        assertEquals(2, result.totalPages());
        assertFalse(result.first());
        assertFalse(result.last());
        verify(bookQueryPort).findDisplayableFor(pageCriteria, "", new UserId("user-123"));
    }

    @Test
    @DisplayName("Given a valid page criteria when find displayable books then maps BookDetailsData to BookDetails")
    public void givenValidPageCriteria_whenFindDisplayableBooks_thenMapsBookDetailsDataToBookDetails() {
        PageCriteria pageCriteria = new PageCriteria(new PageNumber(0), new PageSize(10), SortOrder.NEWEST);
        BookDetailsData bookDetailsData = new BookDetailsData(new BookId(1), new Title("Clean Code"), new AuthorName("Robert C. Martin"),
                new Isbn("1234567890"), new Synopsis("A must-read book..."), new BookCover("/images/cover.jpg"),
                false, true, new FirstName("John"), new LastName("Doe"), 4.5, false);
        Page<BookDetailsData> bookDetailsDataPage = new Page<>(
                List.of(bookDetailsData), 0, 10, 1, 1, true, true
        );

        when(bookQueryPort.findDisplayableFor(pageCriteria, "test", new UserId("user-123")))
                .thenReturn(bookDetailsDataPage);
        when(imageStoragePort.resolveFromUrl("/images/cover.jpg"))
                .thenReturn(new byte[]{1, 2, 3});

        Page<BookDetails> result = bookQueryService.findDisplayableBooks(pageCriteria, "test", new UserId("user-123"));

        assertEquals(1, result.content().size());
        BookDetails bookDetails = result.content().get(0);
        assertEquals(1, bookDetails.id());
        assertEquals("Clean Code", bookDetails.title());
        assertEquals("Robert C. Martin", bookDetails.authorName());
        assertEquals("John Doe", bookDetails.ownerFullName());
        verify(imageStoragePort).resolveFromUrl("/images/cover.jpg");
    }

    @Test
    @DisplayName("Given no books found when find displayable books then returns empty page")
    public void givenNoBooksFound_whenFindDisplayableBooks_thenReturnsEmptyPage() {
        PageCriteria pageCriteria = new PageCriteria(new PageNumber(0), new PageSize(10), SortOrder.NEWEST);
        Page<BookDetailsData> bookDetailsDataPage = new Page<>(
                List.of(), 0, 10, 0, 0, true, true
        );

        when(bookQueryPort.findDisplayableFor(pageCriteria, "NonExistent", new UserId("user-123")))
                .thenReturn(bookDetailsDataPage);

        Page<BookDetails> result = bookQueryService.findDisplayableBooks(pageCriteria, "NonExistent", new UserId("user-123"));

        assertNotNull(result);
        assertTrue(result.content().isEmpty());
        assertEquals(0, result.totalElements());
        assertEquals(0, result.totalPages());
    }

}
