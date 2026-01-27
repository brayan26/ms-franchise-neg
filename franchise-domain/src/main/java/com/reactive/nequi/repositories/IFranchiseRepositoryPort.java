package com.reactive.nequi.repositories;

import com.reactive.nequi.model.Franchise;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IFranchiseRepositoryPort {
    Mono<Franchise> create(Franchise franchise);

    Mono<Franchise> update(Franchise franchise);

    Mono<Franchise> findOne(Long id);

    Flux<Franchise> findAll();
}
