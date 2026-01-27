package com.reactive.nequi.usecases.products;

import com.reactive.nequi.repositories.IProductRepositoryPort;
import com.reactive.nequi.usecases.IUseCaseFunctional;
import reactor.core.publisher.Mono;

public record ProductEraserUseCase(
        IProductRepositoryPort repository) implements IUseCaseFunctional<Long, Mono<Void>> {

    @Override
    public Mono<Void> execute(Long id) {
        return this.repository.deleteProduct(id);
    }
}
