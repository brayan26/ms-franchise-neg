package com.reactive.nequi.usecases.franchise;

import com.reactive.nequi.exceptions.DatabaseUnavailableException;
import com.reactive.nequi.exceptions.ServiceUnavailableException;
import com.reactive.nequi.exceptions.TechnicalException;
import com.reactive.nequi.model.Franchise;
import com.reactive.nequi.mother.FranchiseMotherObject;
import com.reactive.nequi.repositories.IFranchiseRepositoryPort;
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

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
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

    @Test
    @DisplayName("Should be return a ServiceUnavailableException")
    void shouldMapAnyErrorToServiceUnavailableException() {
        Franchise franchise = FranchiseMotherObject.random();

        RuntimeException dbError = new RuntimeException("DB down");
        DatabaseUnavailableException exception = new DatabaseUnavailableException(dbError);

        when(repository.create(franchise))
                .thenReturn(Mono.error(exception));

        StepVerifier.create(useCase.execute(franchise))
                .expectErrorSatisfies(error -> {
                    Assertions.assertInstanceOf(ServiceUnavailableException.class, error);
                    Assertions.assertEquals(Constants.DATABASE_ERROR_MESSAGE, error.getMessage());
                })
                .verify();

        verify(repository).create(franchise);
    }
}
