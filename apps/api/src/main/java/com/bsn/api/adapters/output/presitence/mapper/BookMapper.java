package com.bsn.api.adapters.output.presitence.mapper;

import com.bsn.api.adapters.output.presitence.entity.Book;
import com.bsn.api.adapters.output.presitence.entity.User;

public class BookMapper {

    public static com.bsn.api.core.entity.Book toBook(Book jpaBook) {
        return new com.bsn.api.core.entity.Book(jpaBook.getId(), jpaBook.getTitle(), jpaBook.getAuthorName(),
                jpaBook.getIsbn(), jpaBook.getSynopsis(), jpaBook.getBookCover(), jpaBook.isArchived(),
                jpaBook.isShareable(), jpaBook.getOwner().getKeycloakId());
    }

    public static Book toJpaBook(com.bsn.api.core.entity.Book book, User jpaUser) {
        return Book.builder()
                .id(book.getId())
                .title(book.getTitle())
                .authorName(book.getAuthorName())
                .isbn(book.getIsbn())
                .synopsis(book.getSynopsis())
                .archived(book.isArchived())
                .shareable(book.isShareable())
                .owner(jpaUser)
                .build();
    }

    public static void mergeBook(Book currentJpaBook, com.bsn.api.core.entity.Book updatedBook) {
        currentJpaBook.setTitle(updatedBook.getTitle());
        currentJpaBook.setAuthorName(updatedBook.getAuthorName());
        currentJpaBook.setIsbn(updatedBook.getIsbn());
        currentJpaBook.setSynopsis(updatedBook.getSynopsis());
        currentJpaBook.setArchived(updatedBook.isArchived());
        currentJpaBook.setShareable(updatedBook.isShareable());
    }

}
