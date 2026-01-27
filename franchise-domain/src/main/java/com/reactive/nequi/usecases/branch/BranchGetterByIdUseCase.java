package com.reactive.nequi.usecases.branch;

import com.reactive.nequi.model.Branch;
import com.reactive.nequi.repositories.IBranchRepositoryPort;
import com.reactive.nequi.usecases.IUseCaseFunctional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public record BranchGetterByIdUseCase(
        IBranchRepositoryPort repository) implements IUseCaseFunctional<Long, Mono<Branch>> {

    @Override
    public Mono<Branch> execute(Long id) {
        return this.repository.findOne(id);
    }
}
