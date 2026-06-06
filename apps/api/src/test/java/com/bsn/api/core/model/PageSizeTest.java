package com.bsn.api.core.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PageSizeTest {

    @Test
    @DisplayName("Given a valid value when new then page size is created")
    public void givenValidValue_whenNew_thenPageSizeIsCreated() {
        PageSize pageSize = new PageSize(20);

        assertEquals(20, pageSize.value());
    }

    @Test
    @DisplayName("Given a null value when new then default value is assigned")
    public void givenNullValue_whenNew_thenDefaultValueIsAssigned() {
        PageSize pageSize = new PageSize(null);

        assertEquals(10, pageSize.value());
    }

}

