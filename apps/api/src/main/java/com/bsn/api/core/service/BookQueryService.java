package com.bsn.api.core.service;

import com.bsn.api.core.exception.BookNotFoundException;
import com.bsn.api.core.model.*;
import com.bsn.api.core.port.input.FindDisplayableBooksUseCase;
import com.bsn.api.core.port.input.FindBookDetailsUseCase;
import com.bsn.api.core.port.output.BookQueryPort;
import com.bsn.api.core.port.output.ImageStoragePort;
import com.bsn.api.core.port.output.LoggingPort;
import com.bsn.api.core.value.BookId;
import com.bsn.api.core.value.UserId;

import java.util.List;

public class BookQueryService implements FindBookDetailsUseCase, FindDisplayableBooksUseCase {

    private final BookQueryPort  bookQueryPort;

    private final ImageStoragePort imageStoragePort;

    private final LoggingPort loggingPort;


    public BookQueryService(BookQueryPort bookQueryPort, ImageStoragePort imageStoragePort, LoggingPort loggingPort) {
        this.bookQueryPort = bookQueryPort;
        this.imageStoragePort = imageStoragePort;
        this.loggingPort = loggingPort;
    }

    @Override
    public BookDetails findBookDetails(Integer id, String userId) {
        loggingPort.info("Finding book details by id " + id);

        BookDetailsData bookDetailsData = bookQueryPort.findDetailsById(new BookId(id), new UserId(userId)).orElseThrow(
                () ->  new BookNotFoundException("Book not found with id: " + id)
        );

        return buildBookDetails(bookDetailsData);
    }

    @Override
    public Page<BookDetails> findDisplayableBooks(PageCriteria pageCriteria, String searchTerm, UserId userId) {
        Page<BookDetailsData> bookDetailsDataPage = bookQueryPort.findDisplayableFor(pageCriteria, searchTerm, userId);

        List<BookDetails> bookDetailsList = bookDetailsDataPage.content().stream()
                .map(this::buildBookDetails).toList();

        return new Page<>(bookDetailsList, bookDetailsDataPage.number(), bookDetailsDataPage.size(), bookDetailsDataPage.totalElements(),
                bookDetailsDataPage.totalPages(), bookDetailsDataPage.first(), bookDetailsDataPage.last());
    }

    private BookDetails buildBookDetails(BookDetailsData bookDetailsData) {
        byte[] bookCoverImage = imageStoragePort.resolveFromUrl(bookDetailsData.bookCover().getPath());

        return new BookDetails(bookDetailsData.id().getValue(), bookDetailsData.title().getValue(), bookDetailsData.authorName().getValue(),
                bookDetailsData.isbn().getValue(), bookDetailsData.synopsis().getValue(), bookCoverImage, bookDetailsData.archived(),
                bookDetailsData.shareable(), bookDetailsData.getOwnerFullName(), bookDetailsData.averageRating(), bookDetailsData.isInWaitingList());
    }

}
