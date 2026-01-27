package com.reactive.nequi.utils;

import com.reactive.nequi.factory.DtoConstrainValidationErrorFactory;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public final class DtoValidator {

   private final Validator validator;
   private final DtoConstrainValidationErrorFactory factoryError;


   public <T> void validate(T object) {
      Set<ConstraintViolation<T>> violations = validator.validate(object);
      if (!violations.isEmpty()) {
         List<String> messages = violations.stream()
                 .map(v -> v.getPropertyPath() + ": " + v.getMessage())
                 .toList();
         throw factoryError.constrainError(messages);
      }
   }
}

