package com.reactive.nequi.entry_points.products.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductRequestDto(@NotNull @NotBlank String name, @NotNull Integer stock, @NotNull Long branchId) {
}
