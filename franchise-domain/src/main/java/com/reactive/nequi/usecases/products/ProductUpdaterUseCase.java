package com.reactive.nequi.usecases.products;

import com.reactive.nequi.command.ProductUpdaterCommand;
import com.reactive.nequi.exceptions.ServiceUnavailableException;
import com.reactive.nequi.exceptions.TechnicalException;
import com.reactive.nequi.model.Product;
import com.reactive.nequi.repositories.IProductRepositoryPort;
import com.reactive.nequi.usecases.IUseCaseFunctional;
import com.reactive.nequi.util.BuildErrorServiceUnavailableUtil;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public record ProductUpdaterUseCase(
        ProductLocatorByIdUseCase findByIdUseCase,
        IProductRepositoryPort repository) implements IUseCaseFunctional<ProductUpdaterCommand, Mono<Product>> {

    @Override
    public Mono<Product> execute(ProductUpdaterCommand command) {
        return this.findByIdUseCase.execute(command.id())
                .map(product -> transform(product, command))
                .flatMap(repository::update)
                .onErrorMap(TechnicalException.class, ex ->
                        new ServiceUnavailableException(ex.getMessage(), BuildErrorServiceUnavailableUtil.createError()));
    }

    private Product transform(Product product, ProductUpdaterCommand command) {
        return Product.builder()
                .id(product.id())
                .name(command.product().name())
                .stock(command.product().stock())
                .branchId(command.product().branchId())
                .createdAt(product.createdAt())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
