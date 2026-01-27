package com.reactive.nequi.errors;

public enum FranchiseErrorMessages {
    FRANCHISE_NOT_FOUND(
            "FCS-001",
            "Franchise id %s not found"
    );

    private final String code;
    private final String message;

    FranchiseErrorMessages(String code, String message) {
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
