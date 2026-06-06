package com.bsn.api.adapters.input.rest.mapper;

import com.bsn.api.adapters.input.rest.dto.PageResponse;
import com.bsn.api.core.model.Page;

import java.util.function.Function;

public class PageMapper {

    private PageMapper() {}

    public static <T, R> PageResponse<R> toPageResponse(Page<T> bookDetailsPage, Function<T, R> function) {
        return new PageResponse<>(
                bookDetailsPage.content().stream().map(function).toList(),
                bookDetailsPage.number(),
                bookDetailsPage.size(),
                bookDetailsPage.totalElements(),
                bookDetailsPage.totalPages(),
                bookDetailsPage.first(),
                bookDetailsPage.last()
        );
    }

}
