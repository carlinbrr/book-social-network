package com.bsn.api.core.service;

import com.bsn.api.core.entity.Book;
import com.bsn.api.core.entity.User;
import com.bsn.api.core.model.BookDetails;
import com.bsn.api.core.port.input.FindBookDetailsUseCase;
import com.bsn.api.core.port.output.ImageStoragePort;
import com.bsn.api.core.port.output.LoggingPort;
import com.bsn.api.core.service.helper.BookFinder;
import com.bsn.api.core.service.helper.UserFinder;
import com.bsn.api.core.value.BookId;

public class BookQueryService implements FindBookDetailsUseCase {

    private final BookFinder bookFinder;

    private final UserFinder userFinder;

    private final ImageStoragePort imageStoragePort;

    private final LoggingPort loggingPort;


    public BookQueryService(BookFinder bookFinder, UserFinder userFinder,
                            ImageStoragePort imageStoragePort, LoggingPort loggingPort) {
        this.bookFinder = bookFinder;
        this.userFinder = userFinder;
        this.imageStoragePort = imageStoragePort;
        this.loggingPort = loggingPort;
    }

    @Override
    public BookDetails findBookDetails(Integer id) {
        Book book = bookFinder.findExisting(new BookId(id));

        User user = userFinder.findExisting(book.getOwnerId());

        byte[] bookCoverImage = imageStoragePort.resolveFromUrl(book.getBookCover().getPath());

        // TODO: Add rate and isInWaitingList information
        // TODO: Add logging
        return new BookDetails(book.getId().getValue(), book.getTitle().getValue(), book.getAuthorName().getValue(),
                book.getIsbn().getValue(), book.getSynopsis().getValue(), user.getFullName(), bookCoverImage, 0,
                book.isArchived(), book.isShareable(), true);
    }

}
