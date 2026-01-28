package com.reactive.nequi.adapters;

import com.reactive.nequi.factory.ProductErrorFactory;
import com.reactive.nequi.mappers.ProductMapper;
import com.reactive.nequi.model.BranchProductView;
import com.reactive.nequi.model.Product;
import com.reactive.nequi.persistence.repositories.PostgresProductRepository;
import com.reactive.nequi.repositories.IProductRepositoryPort;
import io.github.resilience4j.bulkhead.Bulkhead;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.reactor.bulkhead.operator.BulkheadOperator;
import io.github.resilience4j.reactor.circuitbreaker.operator.CircuitBreakerOperator;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Component
public record ProductRepositoryAdapter(PostgresProductRepository repository, ProductMapper mapper,
                                       CircuitBreaker circuitBreaker,
                                       Bulkhead bulkhead,
                                       ProductErrorFactory errorFactory) implements IProductRepositoryPort {

    @Override
    public Mono<Product> create(Product product) {
        return this.repository.save(mapper.toEntity(product))
                .map(mapper::toDomain)
                .timeout(Duration.ofSeconds(2))
                .transformDeferred(BulkheadOperator.of(bulkhead))
                .transformDeferred(CircuitBreakerOperator.of(circuitBreaker));
    }

    @Override
    public Mono<Product> update(Product product) {
        return this.repository.save(mapper.toEntity(product))
                .map(mapper::toDomain)
                .timeout(Duration.ofSeconds(2))
                .transformDeferred(BulkheadOperator.of(bulkhead))
                .transformDeferred(CircuitBreakerOperator.of(circuitBreaker));
    }

    @Override
    public Mono<Product> findOne(Long id) {
        return this.repository.findById(id)
                .switchIfEmpty(Mono.error(errorFactory.productNotFound(id)))
                .map(mapper::toDomain)
                .timeout(Duration.ofSeconds(2))
                .transformDeferred(BulkheadOperator.of(bulkhead))
                .transformDeferred(CircuitBreakerOperator.of(circuitBreaker));
    }

    @Override
    public Flux<BranchProductView> findTopProductByBranch(Long franchiseId) {
        return repository.findTopProductByBranch(franchiseId)
                .timeout(Duration.ofSeconds(2))
                .transformDeferred(BulkheadOperator.of(bulkhead))
                .transformDeferred(CircuitBreakerOperator.of(circuitBreaker));
    }

    @Override
    public Mono<Void> deleteProduct(Long id) {
        return this.repository.deleteById(id)
                .timeout(Duration.ofSeconds(2))
                .transformDeferred(BulkheadOperator.of(bulkhead))
                .transformDeferred(CircuitBreakerOperator.of(circuitBreaker));
    }
}
