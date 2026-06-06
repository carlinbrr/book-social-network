package com.bsn.api.core.port.input;

import com.bsn.api.core.model.BookDetails;
import com.bsn.api.core.model.Page;
import com.bsn.api.core.model.PageCriteria;
import com.bsn.api.core.value.UserId;

public interface FindDisplayableBooksUseCase {

    Page<BookDetails> findDisplayableBooks(PageCriteria pageCriteria, String searchTerm, UserId userId);

}
