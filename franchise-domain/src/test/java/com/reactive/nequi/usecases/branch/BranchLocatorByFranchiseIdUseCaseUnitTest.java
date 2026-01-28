package com.reactive.nequi.usecases.branch;

import com.reactive.nequi.model.Branch;
import com.reactive.nequi.mother.BranchMotherObject;
import com.reactive.nequi.repositories.IBranchRepositoryPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

public class BranchLocatorByFranchiseIdUseCaseUnitTest {
    private BranchLocatorByFranchiseIdUseCase useCase;
    private IBranchRepositoryPort repository;

    @BeforeEach
    public void setup() {
        repository = Mockito.mock(IBranchRepositoryPort.class);
        useCase = new BranchLocatorByFranchiseIdUseCase(repository);
    }

    @Test
    public void find_branch_by_franchise_id() {
        Long franchiseId = 1L;
        Branch branch1 = BranchMotherObject.random();
        Branch branch2 = BranchMotherObject.random();

        when(repository.findByFranchiseId(franchiseId)).thenReturn(Flux.just(branch1, branch2));
        StepVerifier.create(useCase.execute(franchiseId))
                .expectNext(branch1)
                .expectNext(branch2)
                .verifyComplete();
    }
}
