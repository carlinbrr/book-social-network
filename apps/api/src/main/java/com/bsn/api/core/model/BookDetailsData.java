package com.bsn.api.core.model;

import com.bsn.api.core.value.*;

public record BookDetailsData(
        BookId id,
        Title title,
        AuthorName authorName,
        Isbn isbn,
        Synopsis synopsis,
        BookCover bookCover,
        boolean archived,
        boolean shareable,
        FirstName ownerFirstName,
        LastName ownerLastName,
        Double averageRating, // TODO: Update rate value
        boolean isInWaitingList
) {

    public BookDetailsData {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }

        if (title == null) {
            throw new IllegalArgumentException("Title cannot be null");
        }

        if (authorName == null) {
            throw new IllegalArgumentException("Author name cannot be null");
        }

        if (isbn == null) {
            throw new IllegalArgumentException("Isbn cannot be null");
        }

        if (synopsis == null) {
            throw new IllegalArgumentException("Synopsis cannot be null");
        }

        if (ownerFirstName == null) {
            throw new IllegalArgumentException("Owner's first name cannot be null");
        }

        if (ownerLastName == null) {
            throw new IllegalArgumentException("Owner's last name cannot be null");
        }

        if (archived && shareable) {
            throw new IllegalArgumentException("Archived book cannot be shareable");
        }
    }


    public String getOwnerFullName() {
        return ownerFirstName.getValue() + " " + ownerLastName.getValue();
    }

}
