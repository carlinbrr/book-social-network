package com.bsn.api.adapters.output.presitence.projection;

public record BookDetailsProjection(
        Integer id,
        String title,
        String authorName,
        String isbn,
        String synopsis,
        String bookCover,
        boolean archived,
        boolean shareable,
        String ownerFirstName,
        String ownerLastName,
        Double averageRating,
        boolean isInWaitingList
) {
}
