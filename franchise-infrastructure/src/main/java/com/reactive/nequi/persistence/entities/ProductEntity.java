package com.reactive.nequi.persistence.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("product")
public record ProductEntity(@Id @Column("id") Long id, @Column("branch_id") Long branchId, @Column("name") String name,
                            @Column("stock") Integer stock, @Column("created_at") LocalDateTime createdAt,
                            @Column("updated_at") LocalDateTime updatedAt) {
}
