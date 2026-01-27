package com.reactive.nequi.command;

import com.reactive.nequi.model.Branch;

public record BranchUpdateCommand(Long id, Branch branch) {
}
