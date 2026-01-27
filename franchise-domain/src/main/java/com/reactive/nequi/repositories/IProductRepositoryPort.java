package com.reactive.nequi.repositories;

import com.reactive.nequi.model.BranchProductView;
import com.reactive.nequi.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IProductRepositoryPort {
    Mono<Product> create(Product product);

    Mono<Product> update(Product product);

    Mono<Product> findOne(Long id);

    Flux<BranchProductView> findTopProductByBranch(Long franchiseId);

    Mono<Void> deleteProduct(Long id);
}
