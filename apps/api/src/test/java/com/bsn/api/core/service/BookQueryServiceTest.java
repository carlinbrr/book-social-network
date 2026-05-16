package com.bsn.api.core.service;

import com.bsn.api.core.exception.BookNotFoundException;
import com.bsn.api.core.model.BookDetailsData;
import com.bsn.api.core.port.output.BookQueryPort;
import com.bsn.api.core.port.output.ImageStoragePort;
import com.bsn.api.core.port.output.LoggingPort;
import com.bsn.api.core.value.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookQueryServiceTest {

    private final BookQueryPort bookQueryPort = mock(BookQueryPort.class);

    private final ImageStoragePort imageStoragePort = mock(ImageStoragePort.class);

    private final LoggingPort loggingPort = mock(LoggingPort.class);

    private final BookQueryService bookQueryService = new BookQueryService(bookQueryPort, imageStoragePort, loggingPort);


    @Test
    @DisplayName("Given an existing id when find book details then book is found")
    public void givenExistingId_whenFindBookDetails_thenBookIsFound() {
        BookId id = new BookId(10);
        String bookCoverUrl = "/temp/img/book.png";

        BookDetailsData bookDetailsData = new BookDetailsData(id, new Title("Clean Code"), new AuthorName("Robert C. Martin"),
                new Isbn("1234567890"), new Synopsis("Synopsis..."), new BookCover(bookCoverUrl), false,
                true, new FirstName("John"), new LastName("Doe"), 4.5);

        when(bookQueryPort.findDetailsById(id)).thenReturn(Optional.of(bookDetailsData));
        when(imageStoragePort.resolveFromUrl(bookCoverUrl)).thenReturn(new byte[2048]);

        assertNotNull(bookQueryService.findBookDetails(10));
    }

    @Test
    @DisplayName("Given an non existing id when find book details then BookNotFounException is thrown")
    public void givenNonExistingId_whenFindBookDetails_thenBookNotFoundExceptionIsThrown() {
        BookId id = new BookId(10);
        String bookCoverUrl = "/temp/img/book.png";

        when(bookQueryPort.findDetailsById(id)).thenReturn(Optional.empty());
        when(imageStoragePort.resolveFromUrl(bookCoverUrl)).thenReturn(new byte[2048]);

        try {
            bookQueryService.findBookDetails(10);
        } catch (BookNotFoundException e) {
            assertEquals("Book not found with id: " + 10, e.getMessage());
            return;
        }

        fail("BookNotFoundException should have been thrown");
    }

}
