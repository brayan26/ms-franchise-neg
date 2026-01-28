package com.reactive.nequi.usecases.branch;

import com.reactive.nequi.command.BranchUpdateCommand;
import com.reactive.nequi.model.Branch;
import com.reactive.nequi.mother.BranchMotherObject;
import com.reactive.nequi.repositories.IBranchRepositoryPort;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class BranchUpdaterUseCaseUnitTest {
    private BranchUpdaterUseCase useCase;
    private BranchGetterByIdUseCase findByIdUseCase;
    private IBranchRepositoryPort repository;

    @BeforeEach
    public void setup() {
        repository = Mockito.mock(IBranchRepositoryPort.class);
        findByIdUseCase = new BranchGetterByIdUseCase(repository);
        useCase = new BranchUpdaterUseCase(findByIdUseCase, repository);
    }

    @Test
    @DisplayName("Should be a branch with updated name")
    public void update_branch() {
        Long id = 1L;
        String newName = "Boston";
        Branch original = BranchMotherObject.random();
        Branch updated = BranchMotherObject.randomUpdated(newName);
        BranchUpdateCommand command = new BranchUpdateCommand(id, original);

        when(repository.findOne(original.id())).thenReturn(Mono.just(original));
        when(findByIdUseCase.execute(original.id())).thenReturn(Mono.just(original));
        when(repository.update(any(Branch.class))).thenReturn(Mono.just(updated));

        // Act & Assert
        StepVerifier.create(useCase.execute(command))
                .assertNext(branch -> Assertions.assertEquals(newName, branch.name()))
                .verifyComplete();
    }
}
