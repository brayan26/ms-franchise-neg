package com.reactive.nequi.persistence.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table("branch")
public record BranchEntity(@Id @Column("id") Long id, @Column("franchise_id") Long franchiseId,
                           @Column("name") String name, @Column("address") String address,
                           @Column("created_at") LocalDateTime createdAt, @Column("created_at") LocalDateTime updatedAt) {
}
