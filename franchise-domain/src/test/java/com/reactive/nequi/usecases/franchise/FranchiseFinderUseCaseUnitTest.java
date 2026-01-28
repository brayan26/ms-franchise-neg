package com.reactive.nequi.usecases.franchise;

import com.reactive.nequi.exceptions.DatabaseUnavailableException;
import com.reactive.nequi.exceptions.ServiceUnavailableException;
import com.reactive.nequi.model.Franchise;
import com.reactive.nequi.mother.FranchiseMotherObject;
import com.reactive.nequi.repositories.IFranchiseRepositoryPort;
import com.reactive.nequi.usecases.Constants;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public final class FranchiseFinderUseCaseUnitTest {
    private IFranchiseRepositoryPort repository;
    private FranchiseFinderUseCase useCase;


    @BeforeEach
    public void setup() {
        repository = Mockito.mock(IFranchiseRepositoryPort.class);
        useCase = new FranchiseFinderUseCase(repository);
    }

    @Test
    @DisplayName("Find all franchises")
    public void findAll_franchises() {
        Franchise franchise1 = FranchiseMotherObject.random();
        Franchise franchise2 = FranchiseMotherObject.random();

        when(repository.findAll()).thenReturn(Flux.just(franchise1,franchise2));
        StepVerifier.create(useCase.execute())
                .expectNext(franchise1)
                .expectNext(franchise2)
                .verifyComplete();
    }

    @Test
    @DisplayName("Should be return a ServiceUnavailableException")
    void shouldMapAnyErrorToServiceUnavailableException() {
        RuntimeException dbError = new RuntimeException("DB down");
        DatabaseUnavailableException exception = new DatabaseUnavailableException(dbError);

        when(repository.findAll()).thenReturn(Flux.error(exception));

        StepVerifier.create(useCase.execute())
                .expectErrorSatisfies(error -> {
                    Assertions.assertInstanceOf(ServiceUnavailableException.class, error);
                    Assertions.assertEquals(Constants.DATABASE_ERROR_MESSAGE, error.getMessage());
                })
                .verify();

        verify(repository).findAll();
    }
}
