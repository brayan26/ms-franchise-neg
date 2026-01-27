package com.reactive.nequi.entry_points.franchise;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class FranchiseRouter {

    @Bean
    public RouterFunction<ServerResponse> franchiseRouterFunction(FranchiseHandler franchiseHandler) {
        return RouterFunctions.route()
                .POST("/franchise/v1/create", franchiseHandler::create)
                .PATCH("/franchise/v1/{id}/update", franchiseHandler::update)
                .GET("/franchise/v1/{id}/findOne", franchiseHandler::findOne)
                .GET("/franchise/v1/findAll", franchiseHandler::findAll)
                .build();
    }
}
