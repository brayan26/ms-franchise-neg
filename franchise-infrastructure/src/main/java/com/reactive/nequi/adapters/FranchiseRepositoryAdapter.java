package com.reactive.nequi.adapters;

import com.reactive.nequi.exceptions.DatabaseUnavailableException;
import com.reactive.nequi.factory.FranchiseErrorFactory;
import com.reactive.nequi.mappers.IFranchiseMapper;
import com.reactive.nequi.model.Franchise;
import com.reactive.nequi.persistence.repositories.PostgresFranchiseRepository;
import com.reactive.nequi.repositories.IFranchiseRepositoryPort;
import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.reactor.bulkhead.operator.BulkheadOperator;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import io.r2dbc.spi.R2dbcException;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public record FranchiseRepositoryAdapter(PostgresFranchiseRepository repository,
                                         FranchiseErrorFactory franchiseErrorFactory,
                                         CircuitBreaker circuitBreaker,
                                         Bulkhead bulkhead,
                                         IFranchiseMapper mapper) implements IFranchiseRepositoryPort {

    @Override
    public Mono<Franchise> create(Franchise franchise) {
        return this.repository.save(mapper.toEntity(franchise))
                .map(this.mapper::toDomain)
                .timeout(Duration.ofSeconds(2))
                .transformDeferred(BulkheadOperator.of(bulkhead))
                .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
                .onErrorMap(R2dbcException.class, DatabaseUnavailableException::new);
    }

    @Override
    public Mono<Franchise> update(Franchise franchise) {
        return this.repository.save(mapper.toEntity(franchise))
                .map(this.mapper::toDomain)
                .timeout(Duration.ofSeconds(2))
                .transformDeferred(BulkheadOperator.of(bulkhead))
                .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
                .onErrorMap(R2dbcException.class, DatabaseUnavailableException::new);
    }

    @Override
    public Mono<Franchise> findOne(Long id) {
        return this.repository.findById(id)
                .switchIfEmpty(Mono.error(franchiseErrorFactory.franchiseNotFound(id)))
                .map(this.mapper::toDomain)
                .timeout(Duration.ofSeconds(2))
                .transformDeferred(BulkheadOperator.of(bulkhead))
                .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
                .onErrorMap(R2dbcException.class, DatabaseUnavailableException::new);
    }

    @Override
    public Flux<Franchise> findAll() {
        return this.repository.findAll()
                .map(this.mapper::toDomain)
                .timeout(Duration.ofSeconds(2))
                .transformDeferred(BulkheadOperator.of(bulkhead))
                .transformDeferred(CircuitBreakerOperator.of(circuitBreaker))
                .onErrorMap(R2dbcException.class, DatabaseUnavailableException::new);
    }
}
