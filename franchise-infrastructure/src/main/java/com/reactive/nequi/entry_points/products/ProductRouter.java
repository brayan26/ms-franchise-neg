package com.reactive.nequi.entry_points.products;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;

@Configuration
public class ProductRouter {
    @Bean
    public RouterFunction<ServerResponse> productRouterFunction(ProductHandler productHandler) {
        return RouterFunctions.route()
                .POST("/product/v1/create", productHandler::create)
                .PATCH("/product/v1/{id}/update", productHandler::update)
                .GET("/product/v1/{id}/findOne", productHandler::findById)
                .GET("/product/v1/{franchiseId}/findTopProductByBranch", productHandler::findTopProductByBranch)
                .DELETE("/product/v1/{id}/delete", productHandler::delete)
                .build();
    }
}
