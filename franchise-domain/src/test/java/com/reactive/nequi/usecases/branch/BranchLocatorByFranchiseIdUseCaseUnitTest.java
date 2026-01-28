package com.reactive.nequi.usecases.branch;

import com.reactive.nequi.exceptions.DatabaseUnavailableException;
import com.reactive.nequi.exceptions.ServiceUnavailableException;
import com.reactive.nequi.model.Branch;
import com.reactive.nequi.mother.BranchMotherObject;
import com.reactive.nequi.repositories.IBranchRepositoryPort;
import com.reactive.nequi.usecases.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BranchLocatorByFranchiseIdUseCaseUnitTest {
    private BranchLocatorByFranchiseIdUseCase useCase;
    private IBranchRepositoryPort repository;

    @BeforeEach
    public void setup() {
        repository = Mockito.mock(IBranchRepositoryPort.class);
        useCase = new BranchLocatorByFranchiseIdUseCase(repository);
    }

    @Test
    @DisplayName("Should be a branch list by franchiseId")
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

    @Test
    @DisplayName("Should be a exception ServiceUnavailableException")
    public void getByIdWithServiceUnavailableException() {
        Long franchiseId = 1L;
        RuntimeException dbError = new RuntimeException("DB down");
        DatabaseUnavailableException exception = new DatabaseUnavailableException(dbError);

        when(repository.findByFranchiseId(franchiseId)).thenReturn(Flux.error(exception));

        StepVerifier.create(useCase.execute(franchiseId))
                .expectErrorSatisfies(error -> {
                    Assertions.assertInstanceOf(ServiceUnavailableException.class, error);
                    Assertions.assertEquals(Constants.DATABASE_ERROR_MESSAGE, error.getMessage());
                })
                .verify();
    }
}
