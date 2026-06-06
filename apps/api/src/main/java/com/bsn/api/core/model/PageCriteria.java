package com.bsn.api.core.model;

public record PageCriteria(
        PageNumber pageNumber,
        PageSize pageSize,
        SortOrder sortOrder
) {
}
