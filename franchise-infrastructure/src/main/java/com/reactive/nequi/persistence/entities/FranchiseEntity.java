package com.reactive.nequi.persistence.entities;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

@Table(name = "franchise")
public record FranchiseEntity(@Id @Column("id") Long id, @Column("name") String name,
                              @Column("created_at") LocalDateTime createdAt,
                              @Column("updated_at") LocalDateTime updatedAt) {

}
