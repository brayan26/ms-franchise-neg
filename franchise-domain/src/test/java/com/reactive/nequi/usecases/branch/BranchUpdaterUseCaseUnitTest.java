package com.reactive.nequi.usecases.branch;

import com.reactive.nequi.command.BranchUpdateCommand;
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
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
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

    @Test
    @DisplayName("Should be return a ServiceUnavailableException")
    void shouldMapAnyErrorToServiceUnavailableException() {
        RuntimeException dbError = new RuntimeException("DB down");
        DatabaseUnavailableException exception = new DatabaseUnavailableException(dbError);
        Long id = 1L;
        Branch original = BranchMotherObject.random();
        BranchUpdateCommand command = new BranchUpdateCommand(id, original);

        when(repository.findOne(original.id())).thenReturn(Mono.just(original));
        when(findByIdUseCase.execute(original.id())).thenReturn(Mono.just(original));
        when(repository.update(any(Branch.class))).thenReturn(Mono.error(exception));

        StepVerifier.create(useCase.execute(command))
                .expectErrorSatisfies(error -> {
                    Assertions.assertInstanceOf(ServiceUnavailableException.class, error);
                    Assertions.assertEquals(Constants.DATABASE_ERROR_MESSAGE, error.getMessage());
                })
                .verify();

    }
}
