package com.bsn.api.core.model;

public record BookDetails(
        Integer id,
        String title,
        String authorName,
        String isbn,
        String synopsis,
        byte[] coverImage,
        boolean archived,
        boolean shareable,
        String ownerFullName,
        Double averageRating,
        boolean isInWaitingList
) {
}
