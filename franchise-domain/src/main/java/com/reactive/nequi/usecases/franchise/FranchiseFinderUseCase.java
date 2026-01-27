package com.reactive.nequi.usecases.franchise;

import com.reactive.nequi.model.Franchise;
import com.reactive.nequi.repositories.IFranchiseRepositoryPort;
import reactor.core.publisher.Flux;

public record FranchiseFinderUseCase(IFranchiseRepositoryPort repository) {

    public Flux<Franchise> execute() {
        return this.repository.findAll();
    }


}
