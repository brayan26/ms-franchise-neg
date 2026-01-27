package com.reactive.nequi.usecases.franchise;

import com.reactive.nequi.model.Franchise;
import com.reactive.nequi.repositories.IFranchiseRepositoryPort;
import com.reactive.nequi.usecases.IUseCaseFunctional;
import reactor.core.publisher.Mono;

public record FranchiseCreatorUseCase(
        IFranchiseRepositoryPort repository) implements IUseCaseFunctional<Franchise, Mono<Franchise>> {

    @Override
    public Mono<Franchise> execute(Franchise franchise) {
        return this.repository.create(franchise);
    }
}
