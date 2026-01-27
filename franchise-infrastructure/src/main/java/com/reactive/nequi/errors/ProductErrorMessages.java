package com.reactive.nequi.errors;

public enum ProductErrorMessages {
    PRODUCT_NOT_FOUND(
            "PD-001",
            "Product id %s not found"
    );

    private final String code;
    private final String message;

    ProductErrorMessages(String code, String message) {
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
