package com.reactive.nequi.usecases.products;

import com.reactive.nequi.model.BranchProductView;
import com.reactive.nequi.repositories.IProductRepositoryPort;
import com.reactive.nequi.usecases.IUseCaseFunctional;
import reactor.core.publisher.Flux;

public record ProductFindTopProductByBranchUseCase(
        IProductRepositoryPort repository) implements IUseCaseFunctional<Long, Flux<BranchProductView>> {

    @Override
    public Flux<BranchProductView> execute(Long franchiseId) {
        return repository.findTopProductByBranch(franchiseId);
    }
}
