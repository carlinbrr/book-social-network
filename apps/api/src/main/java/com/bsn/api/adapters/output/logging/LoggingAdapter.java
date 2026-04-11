package com.bsn.api.adapters.output.logging;

import com.bsn.api.core.port.output.LoggingPort;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class LoggingAdapter implements LoggingPort {

    private static final Logger log = LoggerFactory.getLogger(LoggingAdapter.class);


    @Override
    public void info(String message) {
        log.info(message);
    }

    @Override
    public void warn(String message) {
        log.warn(message);
    }

    @Override
    public void error(String message) {
        log.error(message);
    }

}
