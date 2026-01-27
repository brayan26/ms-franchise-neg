package com.reactive.nequi.entry_points.branch.out;

import java.time.LocalDateTime;

public record BranchResponseDto(Long id, String name, Long franchiseId, String address, LocalDateTime createdAt,
                                LocalDateTime updatedAt) {
}
