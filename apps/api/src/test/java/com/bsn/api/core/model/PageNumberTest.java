package com.bsn.api.core.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PageNumberTest {

    @Test
    @DisplayName("Given a valid value when new then page number is created")
    public void givenValidValue_whenNew_thenPageNumberIsCreated() {
        PageNumber pageNumber = new PageNumber(5);

        assertEquals(5, pageNumber.value());
    }

    @Test
    @DisplayName("Given a null value when new then default value is assigned")
    public void givenNullValue_whenNew_thenDefaultValueIsAssigned() {
        PageNumber pageNumber = new PageNumber(null);

        assertEquals(0, pageNumber.value());
    }

}

