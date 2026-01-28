package com.reactive.nequi.usecases.franchise;

import com.reactive.nequi.exceptions.ServiceUnavailableException;
import com.reactive.nequi.exceptions.TechnicalException;
import com.reactive.nequi.model.Franchise;
import com.reactive.nequi.repositories.IFranchiseRepositoryPort;
import com.reactive.nequi.util.BuildErrorServiceUnavailableUtil;
import reactor.core.publisher.Flux;

public record FranchiseFinderUseCase(IFranchiseRepositoryPort repository) {

    public Flux<Franchise> execute() {
        return this.repository.findAll()
                .onErrorMap(TechnicalException.class, ex ->
                        new ServiceUnavailableException(ex.getMessage(), BuildErrorServiceUnavailableUtil.createError()));
    }


}
