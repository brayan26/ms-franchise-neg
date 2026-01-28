package com.reactive.nequi.util;

import com.reactive.nequi.model.ErrorResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class BuildErrorServiceUnavailableUtil {
    public static ErrorResponse createError() {
        return BuildErrorUtil.create("INT00", "Service unavailable temporally");
    }
}
