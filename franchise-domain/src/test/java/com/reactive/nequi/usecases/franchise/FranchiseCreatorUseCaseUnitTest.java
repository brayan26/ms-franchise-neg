package com.reactive.nequi.usecases.franchise;

import com.reactive.nequi.model.Franchise;
import com.reactive.nequi.mother.FranchiseMotherObject;
import com.reactive.nequi.repositories.IFranchiseRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

public final class FranchiseCreatorUseCaseUnitTest {
    private IFranchiseRepositoryPort repository;
    private FranchiseCreatorUseCase useCase;


    @BeforeEach
    public void setup() {
        repository = Mockito.mock(IFranchiseRepositoryPort.class);
        useCase = new FranchiseCreatorUseCase(repository);
    }

    @Test
    @DisplayName("Create a new franchise successfully")
    public void creates_franchise() {
        Franchise franchise = FranchiseMotherObject.random();

        when(repository.create(franchise)).thenReturn(Mono.just(franchise));
        StepVerifier.create(useCase.execute(franchise))
                .expectNext(franchise)
                .verifyComplete();
    }
}
