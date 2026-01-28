package com.reactive.nequi.exceptions;

public class DatabaseUnavailableException extends TechnicalException {

    public DatabaseUnavailableException(Throwable cause) {
        super("Database unavailable", cause);
    }
}
