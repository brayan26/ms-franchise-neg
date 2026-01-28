package com.reactive.nequi.usecases.franchise;

import com.reactive.nequi.exceptions.DatabaseUnavailableException;
import com.reactive.nequi.exceptions.GenericNotFoundException;
import com.reactive.nequi.exceptions.ServiceUnavailableException;
import com.reactive.nequi.model.Franchise;
import com.reactive.nequi.mother.FranchiseMotherObject;
import com.reactive.nequi.repositories.IFranchiseRepositoryPort;
import com.reactive.nequi.usecases.Constants;
import com.reactive.nequi.util.BuildErrorUtil;
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
public final class FranchiseGetterByIdUseCaseUnitTest {
    private IFranchiseRepositoryPort repository;
    private FranchiseGetterByIdUseCase useCase;

    @BeforeEach
    public void setup() {
        repository = Mockito.mock(IFranchiseRepositoryPort.class);
        useCase = new FranchiseGetterByIdUseCase(repository);
    }

    @Test
    @DisplayName("Get one franchise by id")
    public void get_a_franchise_by_id() {
        Long franchiseId = FranchiseMotherObject.random().id();
        Franchise franchise = FranchiseMotherObject.random();

        when(repository.findOne(franchiseId)).thenReturn(Mono.just(franchise));
        StepVerifier.create(useCase.execute(franchiseId)).expectNext(franchise).verifyComplete();
    }

    @Test
    @DisplayName("Should be return a GenericNotFoundException")
    public void should_return_not_found_exception() {
        String logMessage = "Franchise not found";
        Long franchiseId = 1L;

        GenericNotFoundException ex = new GenericNotFoundException(logMessage, BuildErrorUtil.create("001", "Franchise 1 not found"));

        when(repository.findOne(franchiseId)).thenReturn(Mono.error(ex));
        StepVerifier.create(useCase.execute(franchiseId))
                .expectErrorMatches(throwable -> throwable instanceof GenericNotFoundException e && e.getMessage().equals(logMessage))
                .verify();
    }

    @Test
    @DisplayName("Should be return a ServiceUnavailableException")
    void shouldMapAnyErrorToServiceUnavailableException() {
        Long franchise = FranchiseMotherObject.random().id();

        RuntimeException dbError = new RuntimeException("DB down");
        DatabaseUnavailableException exception = new DatabaseUnavailableException(dbError);

        when(repository.findOne(franchise))
                .thenReturn(Mono.error(exception));

        StepVerifier.create(useCase.execute(franchise))
                .expectErrorSatisfies(error -> {
                    Assertions.assertInstanceOf(ServiceUnavailableException.class, error);
                    Assertions.assertEquals(Constants.DATABASE_ERROR_MESSAGE, error.getMessage());
                })
                .verify();

        verify(repository).findOne(franchise);
    }
}
