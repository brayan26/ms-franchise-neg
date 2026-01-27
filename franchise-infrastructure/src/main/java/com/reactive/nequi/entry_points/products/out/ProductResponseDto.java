package com.reactive.nequi.entry_points.products.out;

import java.time.LocalDateTime;

public record ProductResponseDto(Long id, String name, Integer stock, Long branchId, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
