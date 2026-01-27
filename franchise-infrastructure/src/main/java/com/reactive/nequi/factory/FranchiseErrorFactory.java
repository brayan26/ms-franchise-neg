package com.reactive.nequi.factory;

import com.reactive.nequi.errors.FranchiseErrorMessages;
import com.reactive.nequi.exceptions.GenericNotFoundException;
import com.reactive.nequi.model.ErrorResponse;
import com.reactive.nequi.util.BuildErrorUtil;
import org.springframework.stereotype.Component;

@Component
public record FranchiseErrorFactory() {

    public GenericNotFoundException franchiseNotFound(Long id) {
        return new GenericNotFoundException(message(id), error(id));
    }

    private String message(Long id) {
        return String.format("<FranchiseRepositoryAdapter - findOne> Franchise id %s not found in the database", id);
    }

    private ErrorResponse error(Long id) {
        return BuildErrorUtil.create(FranchiseErrorMessages.FRANCHISE_NOT_FOUND.code(), FranchiseErrorMessages.FRANCHISE_NOT_FOUND.message(id));
    }
}
