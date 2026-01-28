package com.reactive.nequi.usecases.products;

import com.reactive.nequi.exceptions.ServiceUnavailableException;
import com.reactive.nequi.exceptions.TechnicalException;
import com.reactive.nequi.model.Product;
import com.reactive.nequi.repositories.IProductRepositoryPort;
import com.reactive.nequi.usecases.IUseCaseFunctional;
import com.reactive.nequi.util.BuildErrorServiceUnavailableUtil;
import reactor.core.publisher.Mono;

public record ProductCreatorUseCase(
        IProductRepositoryPort repository) implements IUseCaseFunctional<Product, Mono<Product>> {

    @Override
    public Mono<Product> execute(Product product) {
        return this.repository.create(product)
                .onErrorMap(TechnicalException.class, ex ->
                        new ServiceUnavailableException(ex.getMessage(), BuildErrorServiceUnavailableUtil.createError()));
    }
}
