package com.reactive.nequi.persistence.repositories;

import com.reactive.nequi.persistence.entities.BranchEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PostgresBranchRepository extends R2dbcRepository<BranchEntity, Long> {
    Flux<BranchEntity> findAllByFranchiseId(Long franchiseId);
}
