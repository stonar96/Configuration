package com.vanillage.utils.configuration.exceptions;

public final class ConfigurationParserException extends ConfigurationException {
    private static final long serialVersionUID = 1L;

    public ConfigurationParserException() {

    }

    public ConfigurationParserException(String message) {
        super(message);
    }

    public ConfigurationParserException(Throwable cause) {
        super(cause);
    }

    public ConfigurationParserException(String message, Throwable cause) {
        super(message, cause);
    }
}
