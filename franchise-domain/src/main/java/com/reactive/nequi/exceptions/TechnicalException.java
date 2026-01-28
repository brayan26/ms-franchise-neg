package com.reactive.nequi.exceptions;

public abstract class TechnicalException extends RuntimeException {

    protected TechnicalException(String message, Throwable cause) {
        super(message, cause);
    }
}
