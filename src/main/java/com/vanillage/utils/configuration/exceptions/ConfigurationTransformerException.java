package com.vanillage.utils.configuration.exceptions;

public final class ConfigurationTransformerException extends ConfigurationException {
    private static final long serialVersionUID = 1L;

    public ConfigurationTransformerException() {

    }

    public ConfigurationTransformerException(String message) {
        super(message);
    }

    public ConfigurationTransformerException(Throwable cause) {
        super(cause);
    }

    public ConfigurationTransformerException(String message, Throwable cause) {
        super(message, cause);
    }
}
