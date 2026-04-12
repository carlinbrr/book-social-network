package com.bsn.api.core.port.input.command;

public record SaveBookCommand(
        Integer id,
        String title,
        String authorName,
        String isbn,
        String synopsis,
        boolean shareable,
        String ownerId
){
}
