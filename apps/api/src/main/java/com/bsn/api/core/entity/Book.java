package com.bsn.api.core.entity;

import com.bsn.api.core.value.*;

public class Book {

    private BookId id;

    private Title title;

    private AuthorName authorName;

    private Isbn isbn;

    private Synopsis synopsis;

    private BookCover bookCover;

    private boolean archived;

    private boolean shareable;

    private final UserId ownerId;


    public BookId getId() {
        return id;
    }

    public Title getTitle() {
        return title;
    }

    public AuthorName getAuthorName() {
        return authorName;
    }

    public Isbn getIsbn() {
        return isbn;
    }

    public Synopsis getSynopsis() {
        return synopsis;
    }

    public BookCover getBookCover() {
        return bookCover;
    }

    public boolean isArchived() {
        return archived;
    }

    public boolean isShareable() {
        return shareable;
    }

    public UserId getOwnerId() {
        return ownerId;
    }


    private Book(Title title, AuthorName authorName, Isbn isbn, Synopsis synopsis, boolean shareable, UserId ownerId) {
        validateRequired(title, authorName, isbn, synopsis, ownerId);
        this.title = title;
        this.authorName = authorName;
        this.isbn = isbn;
        this.synopsis = synopsis;
        this.archived = false;
        this.shareable = shareable;
        this.ownerId = ownerId;
    }

    private Book(BookId id, Title title, AuthorName authorName, Isbn isbn, Synopsis synopsis, BookCover bookCover,
                 boolean archived, boolean shareable, UserId ownerId) {
        validateRequired(title, authorName, isbn, synopsis, ownerId);

        if (id == null) {
            throw new IllegalArgumentException("Book id cannot be null");
        }

        validateState(archived, shareable);

        this.id = id;
        this.title = title;
        this.authorName = authorName;
        this.isbn = isbn;
        this.synopsis = synopsis;
        this.bookCover = bookCover;
        this.archived = archived;
        this.shareable = shareable;
        this.ownerId = ownerId;
    }

    public static Book createNew(Title title, AuthorName authorName, Isbn isbn, Synopsis synopsis, boolean shareable,
                                 UserId ownerId) {
        return new Book(title, authorName, isbn, synopsis, shareable, ownerId);
    }

    public void updateDetails(Title title, AuthorName authorName, Isbn isbn, Synopsis synopsis, boolean shareable,
                              UserId userId) {
        validateRequired(title, authorName, isbn, synopsis, ownerId);

        if ( !this.ownerId.equals(userId) ) {
            throw new IllegalStateException("Only book owner can update details");
        }

        this.title = title;
        this.authorName = authorName;
        this.isbn = isbn;
        this.synopsis = synopsis;
        this.shareable = shareable;
        if (this.shareable) {
            this.archived = false;
        }
    }

    public static Book restore(BookId id, Title title, AuthorName authorName, Isbn isbn, Synopsis synopsis, BookCover bookCover,
                 boolean archived, boolean shareable, UserId ownerId) {
        return new Book(id, title, authorName, isbn, synopsis, bookCover, archived, shareable, ownerId);
    }

    private static void validateRequired(Title title, AuthorName authorName, Isbn isbn, Synopsis synopsis, UserId ownerId) {
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

        if (ownerId == null) {
            throw new IllegalArgumentException("Owner id cannot be null");
        }
    }

    private static void validateState(boolean archived, boolean shareable) {
        if (archived && shareable) {
            throw new IllegalStateException("Archived book cannot be shareable");
        }
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + (id != null ? id.getValue() : "") +
                ", title='" + title.getValue() + '\'' +
                ", authorName='" + authorName.getValue() + '\'' +
                ", isbn='" + isbn.getValue() + '\'' +
                ", synopsis='" + synopsis.getValue() + '\'' +
                ", bookCover='" + (bookCover != null ? bookCover.getPath() : "") + '\'' +
                ", archived=" + archived +
                ", shareable=" + shareable +
                ", ownerId='" + ownerId.getValue() + '\'' +
                '}';
    }

}
