package com.reactive.nequi.usecases.branch;

import com.reactive.nequi.model.Branch;
import com.reactive.nequi.repositories.IBranchRepositoryPort;
import com.reactive.nequi.usecases.IUseCaseFunctional;
import reactor.core.publisher.Flux;

public record BranchLocatorByFranchiseIdUseCase(
        IBranchRepositoryPort repository) implements IUseCaseFunctional<Long, Flux<Branch>> {

    public Flux<Branch> execute(Long franchiseId) {
        return this.repository.findByFranchiseId(franchiseId);
    }
}
