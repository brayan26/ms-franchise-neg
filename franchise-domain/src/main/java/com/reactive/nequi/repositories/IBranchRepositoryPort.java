package com.reactive.nequi.repositories;

import com.reactive.nequi.model.Branch;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface IBranchRepositoryPort {
    Mono<Branch> create(Branch branch);

    Mono<Branch> update(Branch branch);

    Mono<Branch> findOne(Long id);

    Flux<Branch> findByFranchiseId(Long franchiseId);
}
