package com.reactive.nequi.usecases.products;

import com.reactive.nequi.command.ProductUpdaterCommand;
import com.reactive.nequi.model.Product;
import com.reactive.nequi.repositories.IProductRepositoryPort;
import com.reactive.nequi.usecases.IUseCaseFunctional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public record ProductUpdaterUseCase(
        ProductLocatorByIdUseCase findByIdUseCase,
        IProductRepositoryPort repository) implements IUseCaseFunctional<ProductUpdaterCommand, Mono<Product>> {

    @Override
    public Mono<Product> execute(ProductUpdaterCommand command) {
        return this.findByIdUseCase.execute(command.id())
                .map(product -> transform(product, command))
                .flatMap(repository::update);
    }

    private Product transform(Product product, ProductUpdaterCommand command) {
        return Product.builder()
                .id(product.id())
                .name(command.product().name())
                .stock(command.product().stock())
                .createdAt(product.createdAt())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
