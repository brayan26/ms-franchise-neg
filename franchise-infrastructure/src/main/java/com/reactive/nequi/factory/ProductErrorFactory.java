package com.reactive.nequi.factory;

import com.reactive.nequi.errors.ProductErrorMessages;
import com.reactive.nequi.exceptions.GenericNotFoundException;
import com.reactive.nequi.model.ErrorResponse;
import com.reactive.nequi.util.BuildErrorUtil;
import org.springframework.stereotype.Component;

@Component
public record ProductErrorFactory() {

    public GenericNotFoundException productNotFound(Long id) {
        return new GenericNotFoundException(message(id), error(id));
    }

    private String message(Long id) {
        return String.format("<ProductRepositoryAdapter - findOne> Product id %s not found in the database", id);
    }

    private ErrorResponse error(Long id) {
        return BuildErrorUtil.create(ProductErrorMessages.PRODUCT_NOT_FOUND.code(), ProductErrorMessages.PRODUCT_NOT_FOUND.message(id));
    }
}
