package com.reactive.nequi.usecases.branch;

import com.reactive.nequi.exceptions.ServiceUnavailableException;
import com.reactive.nequi.exceptions.TechnicalException;
import com.reactive.nequi.model.Branch;
import com.reactive.nequi.repositories.IBranchRepositoryPort;
import com.reactive.nequi.usecases.IUseCaseFunctional;
import com.reactive.nequi.util.BuildErrorServiceUnavailableUtil;
import reactor.core.publisher.Mono;

public record BranchCreatorUseCase(
        IBranchRepositoryPort repository) implements IUseCaseFunctional<Branch, Mono<Branch>> {

    public Mono<Branch> execute(Branch branch) {
        return this.repository.create(branch)
                .onErrorMap(TechnicalException.class, ex ->
                        new ServiceUnavailableException(ex.getMessage(), BuildErrorServiceUnavailableUtil.createError()));
    }
}
