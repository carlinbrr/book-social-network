package com.bsn.api.core.model;

public record PageNumber(
        Integer value
) {

    public PageNumber {
        if (value == null) {
            value = 0;
        }
    }

}
