package com.bsn.api.config;

import com.bsn.api.core.port.output.BookRepositoryPort;
import com.bsn.api.core.port.output.ImageStoragePort;
import com.bsn.api.core.port.output.LoggingPort;
import com.bsn.api.core.port.output.UserRepositoryPort;
import com.bsn.api.core.service.BookManagementService;
import com.bsn.api.core.service.BookQueryService;
import com.bsn.api.core.service.helper.BookFinder;
import com.bsn.api.core.service.helper.UserFinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookConfig {

    @Bean
    UserFinder userFinder(UserRepositoryPort userRepositoryPort) {
        return new UserFinder(userRepositoryPort);
    }

    @Bean
    BookFinder bookFinder(BookRepositoryPort bookRepository) {
        return new BookFinder(bookRepository);
    }

    @Bean
    BookManagementService saveBookUseCase(UserFinder userFinder, BookFinder bookFinder, BookRepositoryPort bookRepositoryPort,
                                    LoggingPort loggingPort) {
        return new BookManagementService(userFinder, bookFinder, bookRepositoryPort, loggingPort);
    }

    @Bean
    BookQueryService bookQueryService(BookFinder bookFinder, UserFinder userFinder, ImageStoragePort imageStoragePort,
                                      LoggingPort  loggingPort) {
        return new BookQueryService(bookFinder, userFinder, imageStoragePort, loggingPort);
    }

}
