package com.reactive.nequi.model;

public record BranchProductView(
        Long branchId,
        String branchName,
        Long productId,
        String productName,
        Integer stock
) {
}
