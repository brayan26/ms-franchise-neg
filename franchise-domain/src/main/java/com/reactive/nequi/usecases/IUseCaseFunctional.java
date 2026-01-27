package com.reactive.nequi.usecases;

@FunctionalInterface
public interface IUseCaseFunctional<T, R> {
    R execute(T command);
}
