package com.bsn.api.core.value;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BookIdTest {

    @Test
    @DisplayName("Given a valid input when new then book id is created")
    public void givenValidInput_whenNew_thenBookIdIsCreated() {
        Integer value = 10;

        BookId bookId = new BookId(value);

        assertEquals(10, bookId.getValue());
    }

    @Test
    @DisplayName("Given a null input when new then IllegalArgumentException is thrown")
    public void givenNullInput_whenNew_thenIllegalArgumentExceptionIsThrown() {
        try {
            new BookId(null);
        } catch (IllegalArgumentException e) {
            assertEquals("Book id cannot be null", e.getMessage());
            return;
        }

        fail("IllegalArgumentException should have been thrown");
    }

    @Test
    @DisplayName("Given same inputs when equals then book ids are equal")
    public void givenSameInputs_whenEquals_thenFirstNamesAreEqual() {
        BookId bookId1 = new BookId(10);
        BookId bookId2 = new BookId(10);

        assertEquals(bookId1, bookId2);
    }

    @Test
    @DisplayName("Given different inputs when equals then book ids are different")
    public void givenDifferentInputs_whenEquals_thenFirstNamesAreDifferent() {
        BookId bookId1 = new BookId(10);
        BookId bookId2 = new BookId(20);

        assertNotEquals(bookId1, bookId2);
    }

}
