package com.reactive.nequi.util;

import com.reactive.nequi.model.ErrorResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BuildErrorUtil {

    public static ErrorResponse create(String code, String description) {
        return new ErrorResponse(code, description, LocalDateTime.now(), null);
    }

    public static ErrorResponse create(String code, String description, List<String> errors) {
        return new ErrorResponse(code, description, LocalDateTime.now(), errors);
    }
}
