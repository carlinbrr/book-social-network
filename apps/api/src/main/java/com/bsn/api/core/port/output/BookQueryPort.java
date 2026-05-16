package com.bsn.api.core.port.output;

import com.bsn.api.core.model.BookDetailsData;
import com.bsn.api.core.value.BookId;

import java.util.Optional;

public interface BookQueryPort {

    Optional<BookDetailsData> findDetailsById(BookId id);

}
