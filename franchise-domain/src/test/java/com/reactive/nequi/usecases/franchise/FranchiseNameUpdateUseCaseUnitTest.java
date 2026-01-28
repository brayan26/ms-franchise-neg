package com.reactive.nequi.usecases.franchise;

import com.reactive.nequi.command.FranchiseNamaUpdateCommand;
import com.reactive.nequi.model.Franchise;
import com.reactive.nequi.mother.FranchiseMotherObject;
import com.reactive.nequi.repositories.IFranchiseRepositoryPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class FranchiseNameUpdateUseCaseUnitTest {
    private IFranchiseRepositoryPort repository;
    private FranchiseGetterByIdUseCase findByIdUseCase;
    private FranchiseNameUpdateUseCase useCase;

    @BeforeEach
    void setup() {
        repository = Mockito.mock(IFranchiseRepositoryPort.class);
        findByIdUseCase = Mockito.mock(FranchiseGetterByIdUseCase.class);
        useCase = new FranchiseNameUpdateUseCase(findByIdUseCase, repository);
    }

    @Test
    @DisplayName("Should be update a franchise successfully")
    public void franchiseNameUpdateSuccessfully() {
        Long id = 1L;
        String newName = "OxxO Medellin";

        Franchise original = FranchiseMotherObject.random();
        Franchise updated = FranchiseMotherObject.randomUpdated(newName);

        FranchiseNamaUpdateCommand command =
                new FranchiseNamaUpdateCommand(id, newName);

        when(repository.findOne(original.id())).thenReturn(Mono.just(original));
        when(findByIdUseCase.execute(original.id())).thenReturn(Mono.just(original));
        when(repository.update(any(Franchise.class))).thenReturn(Mono.just(updated));

        // Act & Assert
        StepVerifier.create(useCase.execute(command))
                .assertNext(franchise -> Assertions.assertEquals(newName, franchise.name()))
                .verifyComplete();

        // Verify transformation
        ArgumentCaptor<Franchise> captor = ArgumentCaptor.forClass(Franchise.class);

        verify(repository).update(captor.capture());
    }
}
