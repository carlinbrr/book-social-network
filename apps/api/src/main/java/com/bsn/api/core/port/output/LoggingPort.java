package com.bsn.api.core.port.output;

public interface LoggingPort {

    void debug(String message);

    void info(String message);

    void warn(String message);

    void error(String message);

}
