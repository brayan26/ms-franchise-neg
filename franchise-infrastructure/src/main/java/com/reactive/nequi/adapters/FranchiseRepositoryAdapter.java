package com.reactive.nequi.adapters;

import com.reactive.nequi.factory.FranchiseErrorFactory;
import com.reactive.nequi.mappers.IFranchiseMapper;
import com.reactive.nequi.model.Franchise;
import com.reactive.nequi.persistence.repositories.PostgresFranchiseRepository;
import com.reactive.nequi.repositories.IFranchiseRepositoryPort;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public record FranchiseRepositoryAdapter(PostgresFranchiseRepository repository,
                                         FranchiseErrorFactory franchiseErrorFactory,
                                         IFranchiseMapper mapper) implements IFranchiseRepositoryPort {

    @Override
    public Mono<Franchise> create(Franchise franchise) {
        return this.repository.save(mapper.toEntity(franchise)).map(this.mapper::toDomain);
    }

    @Override
    public Mono<Franchise> update(Franchise franchise) {
        return this.repository.save(mapper.toEntity(franchise)).map(this.mapper::toDomain);
    }

    @Override
    public Mono<Franchise> findOne(Long id) {
        return this.repository.findById(id)
                .switchIfEmpty(Mono.error(franchiseErrorFactory.franchiseNotFound(id)))
                .map(this.mapper::toDomain);
    }

    @Override
    public Flux<Franchise> findAll() {
        return this.repository.findAll().map(this.mapper::toDomain);
    }
}
