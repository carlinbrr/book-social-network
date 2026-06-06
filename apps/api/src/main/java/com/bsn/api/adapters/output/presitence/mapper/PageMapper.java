package com.bsn.api.adapters.output.presitence.mapper;

import com.bsn.api.core.model.Page;

import java.util.function.Function;

public class PageMapper {

    private PageMapper() {}

    public static <T, R> Page<R> toPage(org.springframework.data.domain.Page<T> page, Function<T, R> function) {
        return new Page<>(
                page.getContent().stream().map(function).toList(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast()
        );
    }

}
