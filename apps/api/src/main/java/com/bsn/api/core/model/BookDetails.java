package com.bsn.api.core.model;

public record BookDetails(
        Integer id,
        String title,
        String authorName,
        String isbn,
        String synopsis,
        String owner,
        byte[] cover,
        double rate,
        boolean archived,
        boolean shareable,
        boolean isInWaitingList
) {
}
