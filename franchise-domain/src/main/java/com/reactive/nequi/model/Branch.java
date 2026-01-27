package com.reactive.nequi.model;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record Branch(Long id, String name, Long franchiseId, String address, LocalDateTime createdAt,
                     LocalDateTime updatedAt) {
}
