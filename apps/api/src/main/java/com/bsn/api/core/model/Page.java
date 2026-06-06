package com.bsn.api.core.model;

import java.util.List;

public record Page<T> (
        List<T> content,
        int number,
        int size,
        long totalElements,
        int totalPages,
        boolean first,
        boolean last
) {
}
