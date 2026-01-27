package com.reactive.nequi.adapters;

import com.reactive.nequi.factory.BranchErrorFactory;
import com.reactive.nequi.mappers.BranchMapper;
import com.reactive.nequi.model.Branch;
import com.reactive.nequi.persistence.repositories.PostgresBranchRepository;
import com.reactive.nequi.repositories.IBranchRepositoryPort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public record BranchRepositoryAdapter(PostgresBranchRepository repository,
                                      BranchErrorFactory errorFactory,
                                      BranchMapper mapper) implements IBranchRepositoryPort {

    @Override
    public Mono<Branch> create(Branch branch) {
        return this.repository.save(mapper.toEntity(branch)).map(mapper::toDomain);
    }

    @Override
    public Mono<Branch> update(Branch branch) {
        return this.repository.save(mapper.toEntity(branch)).map(mapper::toDomain);
    }

    @Override
    public Mono<Branch> findOne(Long id) {
        return this.repository.findById(id)
                .switchIfEmpty(Mono.error(errorFactory.branchNotFound(id)))
                .map(mapper::toDomain);
    }

    @Override
    public Flux<Branch> findByFranchiseId(Long franchiseId) {
        return this.repository.findAllByFranchiseId(franchiseId).map(this.mapper::toDomain);
    }
}
