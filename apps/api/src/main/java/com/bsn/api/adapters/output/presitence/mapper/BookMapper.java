package com.bsn.api.adapters.output.presitence.mapper;

import com.bsn.api.adapters.output.presitence.entity.Book;
import com.bsn.api.adapters.output.presitence.entity.User;
import com.bsn.api.core.value.*;

public class BookMapper {

    public static com.bsn.api.core.entity.Book toBook(Book jpaBook) {
        return com.bsn.api.core.entity.Book.restore(new BookId(jpaBook.getId()), new Title(jpaBook.getTitle()),
                new AuthorName(jpaBook.getAuthorName()), new Isbn(jpaBook.getIsbn()), new Synopsis(jpaBook.getSynopsis()),
                jpaBook.getBookCover() != null ? new BookCover(jpaBook.getBookCover()) : null,
                jpaBook.isArchived(), jpaBook.isShareable(),
                new UserId(jpaBook.getOwner().getKeycloakId()));
    }

    public static Book toJpaBook(com.bsn.api.core.entity.Book book, User jpaUser) {
        return Book.builder()
                .id(book.getId() != null ? book.getId().getValue() : null)
                .title(book.getTitle().getValue())
                .authorName(book.getAuthorName().getValue())
                .isbn(book.getIsbn().getValue())
                .synopsis(book.getSynopsis().getValue())
                .archived(book.isArchived())
                .shareable(book.isShareable())
                .owner(jpaUser)
                .build();
    }

    public static void mergeBook(Book currentJpaBook, com.bsn.api.core.entity.Book updatedBook) {
        currentJpaBook.setTitle(updatedBook.getTitle().getValue());
        currentJpaBook.setAuthorName(updatedBook.getAuthorName().getValue());
        currentJpaBook.setIsbn(updatedBook.getIsbn().getValue());
        currentJpaBook.setSynopsis(updatedBook.getSynopsis().getValue());
        currentJpaBook.setArchived(updatedBook.isArchived());
        currentJpaBook.setShareable(updatedBook.isShareable());
    }

}
