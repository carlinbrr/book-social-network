package com.bsn.api.core.entity;

import com.bsn.api.core.exception.BookDetailsCannotChangeException;

public class Book {

    private Integer id;

    private String title;

    private String authorName;

    private String isbn;

    private String synopsis;

    private String bookCover;

    private boolean archived;

    private boolean shareable;

    private final String ownerId;


    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthorName() {
        return authorName;
    }

    public String getIsbn() {
        return isbn;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public String getBookCover() {
        return bookCover;
    }

    public boolean isArchived() {
        return archived;
    }

    public boolean isShareable() {
        return shareable;
    }

    public String getOwnerId() {
        return ownerId;
    }


    public Book(String title, String authorName, String isbn, String synopsis, boolean shareable, String ownerId) {
        this.title = title;
        this.authorName = authorName;
        this.isbn = isbn;
        this.synopsis = synopsis;
        this.archived = false;
        this.shareable = shareable;
        this.ownerId = ownerId;
    }

    public Book(Integer id, String title, String authorName, String isbn, String synopsis, String bookCover,
                boolean archived, boolean shareable, String ownerId) {
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

    public void updateDetails(String title, String authorName, String isbn, String synopsis, boolean shareable,  String ownerId) {
        if ( !this.ownerId.equals(ownerId) ) {
            throw new BookDetailsCannotChangeException("Only book owner can update details");
        }

        this.title = title;
        this.authorName = authorName;
        this.isbn = isbn;
        this.synopsis = synopsis;
        this.shareable = shareable;

        if ( shareable ) {
            this.archived = false;
        }
    }

    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", authorName='" + authorName + '\'' +
                ", isbn='" + isbn + '\'' +
                ", synopsis='" + synopsis + '\'' +
                ", bookCover='" + bookCover + '\'' +
                ", archived=" + archived +
                ", shareable=" + shareable +
                ", ownerId='" + ownerId + '\'' +
                '}';
    }

}
