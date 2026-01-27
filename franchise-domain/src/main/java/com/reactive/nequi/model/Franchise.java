package com.reactive.nequi.model;

import lombok.Builder;

import java.time.LocalDateTime;

@Builder
public record Franchise(String id, String name, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
