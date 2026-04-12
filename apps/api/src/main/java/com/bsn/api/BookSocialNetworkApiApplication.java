package com.bsn.api;

import com.bsn.api.core.port.input.SaveBookUseCase;
import com.bsn.api.core.port.input.SaveUserUseCase;
import com.bsn.api.core.port.output.BookRepositoryPort;
import com.bsn.api.core.port.output.LoggingPort;
import com.bsn.api.core.port.output.UserRepositoryPort;
import com.bsn.api.core.service.SaveBookService;
import com.bsn.api.core.service.SaveUserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "applicationAuditAware")
@EnableAsync
public class BookSocialNetworkApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(BookSocialNetworkApiApplication.class, args);
	}

}
