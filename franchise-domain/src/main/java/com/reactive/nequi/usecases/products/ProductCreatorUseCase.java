package com.reactive.nequi.usecases.products;

import com.reactive.nequi.model.Product;
import com.reactive.nequi.repositories.IProductRepositoryPort;
import com.reactive.nequi.usecases.IUseCaseFunctional;
import reactor.core.publisher.Mono;

public record ProductCreatorUseCase(
        IProductRepositoryPort repository) implements IUseCaseFunctional<Product, Mono<Product>> {

    @Override
    public Mono<Product> execute(Product product) {
        return this.repository.create(product);
    }
}
