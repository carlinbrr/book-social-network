package com.bsn.api.core.port.output;

import com.bsn.api.core.model.*;
import com.bsn.api.core.value.BookId;
import com.bsn.api.core.value.UserId;

import java.util.Optional;

public interface BookQueryPort {

    Optional<BookDetailsData> findDetailsById(BookId bookId, UserId userId);

    Page<BookDetailsData> findDisplayableFor(PageCriteria pageCriteria, String searchTerm, UserId userId);

}
