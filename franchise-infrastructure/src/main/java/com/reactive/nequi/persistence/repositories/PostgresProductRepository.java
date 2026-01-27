package com.reactive.nequi.persistence.repositories;

import com.reactive.nequi.model.BranchProductView;
import com.reactive.nequi.persistence.entities.ProductEntity;
import org.springframework.data.r2dbc.repository.Query;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface PostgresProductRepository extends R2dbcRepository<ProductEntity, Long> {
    @Query("""
                SELECT DISTINCT ON (b.id)
                       b.id   AS branch_id,
                       b.name AS branch_name,
                       p.id   AS product_id,
                       p.name AS product_name,
                       p.stock
                FROM branch b
                JOIN product p ON p.branch_id = b.id
                WHERE b.franchise_id = :franchiseId
                ORDER BY b.id, p.stock DESC
            """)
    Flux<BranchProductView> findTopProductByBranch(Long franchiseId);
}
