package com.reactive.nequi.model;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record Product(Long id, String name, Integer stock, Long branchId, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
