package com.bsn.api.config;

import com.bsn.api.core.port.input.SaveUserUseCase;
import com.bsn.api.core.port.output.LoggingPort;
import com.bsn.api.core.port.output.UserRepositoryPort;
import com.bsn.api.core.service.SaveUserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserConfig {

    @Bean
    SaveUserUseCase saveUserUseCase(UserRepositoryPort userRepositoryPort, LoggingPort loggingPort) {
        return new SaveUserService(userRepositoryPort, loggingPort);
    }

}
