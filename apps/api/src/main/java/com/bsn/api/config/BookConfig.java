package com.bsn.api.config;

import com.bsn.api.core.port.input.SaveBookUseCase;
import com.bsn.api.core.port.output.BookRepositoryPort;
import com.bsn.api.core.port.output.LoggingPort;
import com.bsn.api.core.port.output.UserRepositoryPort;
import com.bsn.api.core.service.SaveBookService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BookConfig {

    @Bean
    SaveBookUseCase saveBookUseCase(BookRepositoryPort bookRepositoryPort, UserRepositoryPort userRepositoryPort,
                                    LoggingPort loggingPort) {
        return new SaveBookService(bookRepositoryPort, userRepositoryPort, loggingPort);
    }

}
