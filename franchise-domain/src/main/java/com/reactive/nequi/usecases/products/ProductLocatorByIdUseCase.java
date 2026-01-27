package com.reactive.nequi.usecases.products;

import com.reactive.nequi.model.Product;
import com.reactive.nequi.repositories.IProductRepositoryPort;
import com.reactive.nequi.usecases.IUseCaseFunctional;
import reactor.core.publisher.Mono;

public record ProductLocatorByIdUseCase(
        IProductRepositoryPort repository) implements IUseCaseFunctional<Long, Mono<Product>> {

    public Mono<Product> execute(Long id) {
        return this.repository.findOne(id);
    }
}
