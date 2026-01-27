package com.reactive.nequi.entry_points.franchise.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FranchiseRequestDto(@NotNull @NotBlank String name) {
}
