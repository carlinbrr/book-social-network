package com.bsn.api.core.service;

import com.bsn.api.core.exception.BookNotFoundException;
import com.bsn.api.core.model.BookDetails;
import com.bsn.api.core.model.BookDetailsData;
import com.bsn.api.core.port.input.FindBookDetailsUseCase;
import com.bsn.api.core.port.output.BookQueryPort;
import com.bsn.api.core.port.output.ImageStoragePort;
import com.bsn.api.core.port.output.LoggingPort;
import com.bsn.api.core.value.BookId;

public class BookQueryService implements FindBookDetailsUseCase {

    private final BookQueryPort  bookQueryPort;

    private final ImageStoragePort imageStoragePort;

    private final LoggingPort loggingPort;


    public BookQueryService(BookQueryPort bookQueryPort, ImageStoragePort imageStoragePort, LoggingPort loggingPort) {
        this.bookQueryPort = bookQueryPort;
        this.imageStoragePort = imageStoragePort;
        this.loggingPort = loggingPort;
    }

    @Override
    public BookDetails findBookDetails(Integer id) {
        loggingPort.info("Finding book details by id " + id);

        BookDetailsData bookDetailsData = bookQueryPort.findDetailsById(new BookId(id)).orElseThrow(
                () ->  new BookNotFoundException("Book not found with id: " + id)
        );

        byte[] bookCoverImage = imageStoragePort.resolveFromUrl(bookDetailsData.bookCover().getPath());

        loggingPort.info("Book details found with id " + id);

        return new BookDetails(bookDetailsData.id().getValue(), bookDetailsData.title().getValue(), bookDetailsData.authorName().getValue(),
                bookDetailsData.isbn().getValue(), bookDetailsData.synopsis().getValue(), bookCoverImage, bookDetailsData.archived(),
                bookDetailsData.shareable(), bookDetailsData.getOwnerFullName(), bookDetailsData.averageRating());
    }

}
