package com.reactive.nequi.usecases.branch;

import com.reactive.nequi.exceptions.GenericNotFoundException;
import com.reactive.nequi.model.Branch;
import com.reactive.nequi.mother.BranchMotherObject;
import com.reactive.nequi.repositories.IBranchRepositoryPort;
import com.reactive.nequi.util.BuildErrorUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

public class BranchGetterByIdUseCaseUnitTest {
    private IBranchRepositoryPort repository;
    private BranchGetterByIdUseCase useCase;

    @BeforeEach
    public void setUp() {
        repository = Mockito.mock(IBranchRepositoryPort.class);
        useCase = new BranchGetterByIdUseCase(repository);
    }

    @Test
    @DisplayName("Should be a existing branch")
    public void getById() {
        Long id = 1L;
        String name = "Prado";
        Branch branch = BranchMotherObject.random();

        when(repository.findOne(id)).thenReturn(Mono.just(branch));

        StepVerifier.create(useCase.execute(id))
                .assertNext(result -> {
                    Assertions.assertEquals(1L, result.id());
                    Assertions.assertEquals(name, result.name());
                }).verifyComplete();
    }

    @Test
    @DisplayName("Should be a exception GenericNotFoundException")
    public void getByIdWithError() {
        String logMessage = "Branch not found";
        Long branchId = 1L;
        GenericNotFoundException ex = new GenericNotFoundException(logMessage, BuildErrorUtil.create("B002", "Branch 1 not found"));

        when(repository.findOne(branchId)).thenReturn(Mono.error(ex));

        when(repository.findOne(branchId)).thenReturn(Mono.error(ex));
        StepVerifier.create(useCase.execute(branchId))
                .expectErrorMatches(throwable -> throwable instanceof GenericNotFoundException e && e.getMessage().equals(logMessage))
                .verify();
    }
}
