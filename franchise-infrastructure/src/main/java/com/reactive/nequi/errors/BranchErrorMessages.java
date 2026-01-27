package com.reactive.nequi.errors;

public enum BranchErrorMessages {
    BRANCH_NOT_FOUND(
            "B-001",
            "Branch id %s not found"
    );

    private final String code;
    private final String message;

    BranchErrorMessages(String code, String message) {
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
