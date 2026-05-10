package com.bsn.api.core.service;

import com.bsn.api.core.entity.Book;
import com.bsn.api.core.entity.User;
import com.bsn.api.core.port.output.ImageStoragePort;
import com.bsn.api.core.port.output.LoggingPort;
import com.bsn.api.core.service.helper.BookFinder;
import com.bsn.api.core.service.helper.UserFinder;
import com.bsn.api.core.value.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookQueryServiceTest {

    private final BookFinder bookFinder = mock(BookFinder.class);

    private final UserFinder userFinder = mock(UserFinder.class);

    private final ImageStoragePort imageStoragePort = mock(ImageStoragePort.class);

    private final LoggingPort loggingPort = mock(LoggingPort.class);

    private final BookQueryService bookQueryService = new BookQueryService(bookFinder, userFinder, imageStoragePort,
            loggingPort);


    @Test
    @DisplayName("Given an existing id when find book details then book is found")
    public void givenExistingId_whenFindBookDetails_thenBookIsFound() {
        BookId id = new BookId(10);
        UserId ownerId = new UserId("123-abc");
        String bookCoverUrl = "/temp/img/book.png";

        Book book = Book.restore(id, new Title("Clean Code"), new AuthorName("Robert C. Martin"),
                new Isbn("1234567890"), new Synopsis("Synopsis..."), new BookCover(bookCoverUrl), false,
                true, new UserId("123-abc"));
        User user = User.restore(ownerId, new FirstName("John"), new LastName("Doe"), new Email("john@mail.com"));


        when(bookFinder.findExisting(id)).thenReturn(book);
        when(userFinder.findExisting(ownerId)).thenReturn(user);
        when(imageStoragePort.resolveFromUrl(bookCoverUrl)).thenReturn(new byte[2048]);

        assertNotNull(bookQueryService.findBookDetails(10));
    }

}
