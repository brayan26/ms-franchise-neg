package com.reactive.nequi.factory;

import com.reactive.nequi.errors.BranchErrorMessages;
import com.reactive.nequi.exceptions.GenericNotFoundException;
import com.reactive.nequi.model.ErrorResponse;
import com.reactive.nequi.util.BuildErrorUtil;
import org.springframework.stereotype.Component;

@Component
public record BranchErrorFactory() {
    public GenericNotFoundException branchNotFound(Long id) {
        return new GenericNotFoundException(message(id), error(id));
    }

    private String message(Long id) {
        return String.format("<BranchRepositoryAdapter - findOne> Branch id %s not found in the database", id);
    }

    private ErrorResponse error(Long id) {
        return BuildErrorUtil.create(BranchErrorMessages.BRANCH_NOT_FOUND.code(), BranchErrorMessages.BRANCH_NOT_FOUND.message(id));
    }
}
