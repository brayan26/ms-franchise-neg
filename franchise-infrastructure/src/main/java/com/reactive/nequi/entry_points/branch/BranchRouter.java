package com.reactive.nequi.entry_points.branch;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class BranchRouter {

    @Bean
    public RouterFunction<ServerResponse> branchRouterFunction(BranchHandler branchOfficeHandler) {
        return RouterFunctions.route()
                .POST("/branch/v1/create", branchOfficeHandler::create)
                .PATCH("/branch/v1/{id}/update", branchOfficeHandler::update)
                .GET("/branch/v1/{franchiseId}/findAll", branchOfficeHandler::findByFranchiseId)
                .build();
    }
}
