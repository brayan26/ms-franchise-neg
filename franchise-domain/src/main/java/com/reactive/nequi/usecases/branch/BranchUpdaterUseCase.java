package com.reactive.nequi.usecases.branch;

import com.reactive.nequi.command.BranchUpdateCommand;
import com.reactive.nequi.model.Branch;
import com.reactive.nequi.repositories.IBranchRepositoryPort;
import com.reactive.nequi.usecases.IUseCaseFunctional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public record BranchUpdaterUseCase(
        BranchGetterByIdUseCase findByIdUseCase,
        IBranchRepositoryPort repository) implements IUseCaseFunctional<BranchUpdateCommand, Mono<Branch>> {

    public Mono<Branch> execute(BranchUpdateCommand command) {
        return this.findByIdUseCase.execute(command.id())
                .map(branch -> transform(branch, command))
                .flatMap(this.repository::update);
    }

    private Branch transform(Branch branch, BranchUpdateCommand command) {
        return Branch.builder()
                .id(branch.id())
                .name(command.branch().name())
                .address(command.branch().address())
                .franchiseId(command.branch().franchiseId())
                .createdAt(branch.createdAt())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
