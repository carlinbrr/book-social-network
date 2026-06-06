package com.bsn.api.core.model;

public record PageSize(
        Integer value
) {

    public PageSize {
        if (value == null) {
            value = 10;
        }
    }

}
