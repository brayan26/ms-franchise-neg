package com.reactive.nequi.usecases.products;

import com.reactive.nequi.exceptions.ServiceUnavailableException;
import com.reactive.nequi.exceptions.TechnicalException;
import com.reactive.nequi.repositories.IProductRepositoryPort;
import com.reactive.nequi.usecases.IUseCaseFunctional;
import com.reactive.nequi.util.BuildErrorServiceUnavailableUtil;
import reactor.core.publisher.Mono;

public record ProductEraserUseCase(
        IProductRepositoryPort repository) implements IUseCaseFunctional<Long, Mono<Void>> {

    @Override
    public Mono<Void> execute(Long id) {
        return this.repository.deleteProduct(id)
                .onErrorMap(TechnicalException.class, ex ->
                        new ServiceUnavailableException(ex.getMessage(), BuildErrorServiceUnavailableUtil.createError()));
    }
}
