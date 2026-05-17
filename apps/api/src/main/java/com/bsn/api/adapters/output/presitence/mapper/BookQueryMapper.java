package com.bsn.api.adapters.output.presitence.mapper;

import com.bsn.api.adapters.output.presitence.projection.BookDetailsProjection;
import com.bsn.api.core.model.BookDetailsData;
import com.bsn.api.core.value.*;

public class BookQueryMapper {

    public static BookDetailsData toBookDetailsData(BookDetailsProjection bookDetailsProjection) {
        BookId bookId = new BookId(bookDetailsProjection.id());
        Title  title = new Title(bookDetailsProjection.title());
        AuthorName authorName = new AuthorName(bookDetailsProjection.authorName());
        Isbn isbn = new Isbn(bookDetailsProjection.isbn());
        Synopsis synopsis = new Synopsis(bookDetailsProjection.synopsis());
        BookCover bookCover = new BookCover(bookDetailsProjection.bookCover());
        boolean archived = bookDetailsProjection.archived();
        boolean shareable = bookDetailsProjection.shareable();
        FirstName ownerFirstName = new FirstName(bookDetailsProjection.ownerFirstName());
        LastName ownerLastName = new LastName(bookDetailsProjection.ownerLastName());
        Double averageRating =  bookDetailsProjection.averageRating();

        return new BookDetailsData(
                bookId, title, authorName, isbn, synopsis, bookCover, archived, shareable, ownerFirstName,
                ownerLastName, averageRating
        );
    }

}
