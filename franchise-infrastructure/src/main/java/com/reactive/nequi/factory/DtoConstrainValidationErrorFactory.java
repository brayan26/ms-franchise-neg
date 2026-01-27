package com.reactive.nequi.factory;

import com.reactive.nequi.errors.DtoValidationErrorMessages;
import com.reactive.nequi.exceptions.GenericBadRequestException;
import com.reactive.nequi.model.ErrorResponse;
import com.reactive.nequi.util.BuildErrorUtil;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public final class DtoConstrainValidationErrorFactory {

    public GenericBadRequestException constrainError(List<String> errors) {
        return new GenericBadRequestException(message(), error(errors));
    }

    private String message() {
        return "<DtoValidator - validate> Error validating dto constrains";
    }

    private ErrorResponse error(List<String> errors) {
        return BuildErrorUtil.create(
                DtoValidationErrorMessages.INVALID_DTO.code(), DtoValidationErrorMessages.INVALID_DTO.message(), errors);
    }
}
