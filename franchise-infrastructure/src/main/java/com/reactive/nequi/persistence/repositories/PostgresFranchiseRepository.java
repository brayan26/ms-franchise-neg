package com.reactive.nequi.persistence.repositories;

import com.reactive.nequi.persistence.entities.FranchiseEntity;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostgresFranchiseRepository extends R2dbcRepository<FranchiseEntity, Long> {
}
