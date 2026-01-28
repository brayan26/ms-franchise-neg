package com.reactive.nequi.usecases.products;

import com.reactive.nequi.exceptions.ServiceUnavailableException;
import com.reactive.nequi.exceptions.TechnicalException;
import com.reactive.nequi.model.BranchProductView;
import com.reactive.nequi.repositories.IProductRepositoryPort;
import com.reactive.nequi.usecases.IUseCaseFunctional;
import com.reactive.nequi.util.BuildErrorServiceUnavailableUtil;
import reactor.core.publisher.Flux;

public record ProductFindTopProductByBranchUseCase(
        IProductRepositoryPort repository) implements IUseCaseFunctional<Long, Flux<BranchProductView>> {

    @Override
    public Flux<BranchProductView> execute(Long franchiseId) {
        return repository.findTopProductByBranch(franchiseId)
                .onErrorMap(TechnicalException.class, ex ->
                        new ServiceUnavailableException(ex.getMessage(), BuildErrorServiceUnavailableUtil.createError()));
    }
}
