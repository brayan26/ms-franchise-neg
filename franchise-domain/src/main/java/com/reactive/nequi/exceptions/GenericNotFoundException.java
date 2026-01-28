package com.reactive.nequi.exceptions;

import com.reactive.nequi.model.ErrorResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class GenericNotFoundException extends DomainException {
   private final ErrorResponse error;

   public GenericNotFoundException(String message, ErrorResponse error) {
      super(message);
      this.error = error;
   }
}
