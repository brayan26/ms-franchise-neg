package com.reactive.nequi.errors;

public enum DtoValidationErrorMessages {
    INVALID_DTO(
            "DTO-001",
            "Invalid constrains"
    );

    private final String code;
    private final String message;

    DtoValidationErrorMessages(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String code() {
        return code;
    }

    public String message(Object... args) {
        return String.format(message, args);
    }
}
