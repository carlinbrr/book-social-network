package com.bsn.api.core.port.output;

public interface LoggingPort {

    void info(String message);

    void warn(String message);

    void error(String message);

}
