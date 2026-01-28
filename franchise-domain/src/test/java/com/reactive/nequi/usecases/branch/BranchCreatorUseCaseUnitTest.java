package com.reactive.nequi.usecases.branch;

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

import static org.mockito.Mockito.when;

public class BranchCreatorUseCaseUnitTest {
    private IBranchRepositoryPort repository;
    private BranchCreatorUseCase useCase;

    @BeforeEach
    public void setUp() {
        repository = Mockito.mock(IBranchRepositoryPort.class);
        useCase = new BranchCreatorUseCase(repository);
    }

    @Test
    @DisplayName("Should be create a new branch")
    public void createsNewBranch() {
        String name = "Prado";
        Branch branch = BranchMotherObject.random();
        when(repository.create(branch)).thenReturn(Mono.just(branch));

        StepVerifier.create(useCase.execute(branch))
                .assertNext(response -> {
                    Assertions.assertEquals(1L, response.id());
                    Assertions.assertEquals(name, response.name());
                }).verifyComplete();
    }
}
