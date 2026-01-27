package com.reactive.nequi.utils;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.web.reactive.function.server.ServerRequest;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GetPathVariableUtil {

    public static Long getId(ServerRequest request) {
        return Long.parseLong(request.pathVariable("id"));
    }

    public static Long getId(ServerRequest request, String pathId) {
        return Long.parseLong(request.pathVariable(pathId));
    }
}
