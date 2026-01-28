package com.reactive.nequi.usecases.branch;

import com.reactive.nequi.exceptions.ServiceUnavailableException;
import com.reactive.nequi.exceptions.TechnicalException;
import com.reactive.nequi.model.Branch;
import com.reactive.nequi.repositories.IBranchRepositoryPort;
import com.reactive.nequi.usecases.IUseCaseFunctional;
import com.reactive.nequi.util.BuildErrorServiceUnavailableUtil;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public record BranchGetterByIdUseCase(
        IBranchRepositoryPort repository) implements IUseCaseFunctional<Long, Mono<Branch>> {

    @Override
    public Mono<Branch> execute(Long id) {
        return this.repository.findOne(id)
                .onErrorMap(TechnicalException.class, ex ->
                        new ServiceUnavailableException(ex.getMessage(), BuildErrorServiceUnavailableUtil.createError()));
    }
}
