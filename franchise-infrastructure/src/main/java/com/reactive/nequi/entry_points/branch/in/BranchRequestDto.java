package com.reactive.nequi.entry_points.branch.in;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record BranchRequestDto(@NotNull @NotBlank String name, @NotNull @NotBlank String address, @NotNull Long franchiseId) {
}
