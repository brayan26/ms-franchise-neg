package com.reactive.nequi.usecases.franchise;

import com.reactive.nequi.command.FranchiseNamaUpdateCommand;
import com.reactive.nequi.model.Franchise;
import com.reactive.nequi.repositories.IFranchiseRepositoryPort;
import com.reactive.nequi.usecases.IUseCaseFunctional;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

public record FranchiseNameUpdateUseCase(FranchiseGetterByIdUseCase findFranchiseByIdUseCase,
                                         IFranchiseRepositoryPort repository) implements IUseCaseFunctional<FranchiseNamaUpdateCommand, Mono<Franchise>> {

    @Override
    public Mono<Franchise> execute(FranchiseNamaUpdateCommand command) {
        return this.findFranchiseByIdUseCase.execute(command.id())
                .map(franchise -> this.transformFranchise(franchise, command.name()))
                .flatMap(repository::update);
    }

    private Franchise transformFranchise(Franchise franchise, String name) {
        return Franchise.builder()
                .id(franchise.id())
                .name(name)
                .createdAt(franchise.createdAt())
                .updatedAt(LocalDateTime.now())
                .build();
    }
}
