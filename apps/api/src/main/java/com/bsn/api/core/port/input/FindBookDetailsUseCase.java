package com.bsn.api.core.port.input;

import com.bsn.api.core.model.BookDetails;

public interface FindBookDetailsUseCase {

    BookDetails findBookDetails(Integer bookId);

}
