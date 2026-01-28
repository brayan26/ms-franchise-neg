package com.reactive.nequi.server;

import com.reactive.nequi.exceptions.GenericBadRequestException;
import com.reactive.nequi.exceptions.GenericNotFoundException;
import com.reactive.nequi.exceptions.ServiceUnavailableException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.webflux.error.ErrorWebExceptionHandler;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import tools.jackson.databind.ObjectMapper;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
@RequiredArgsConstructor
public final class GlobalExceptionHandler implements ErrorWebExceptionHandler {
   private final ObjectMapper objectMapper;

   @Override
   public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
      log.error("ErrorMapping: {}", ex.getLocalizedMessage());

      ErrorResponse errorResponse = mapExceptionToErrorResponse(ex);


      exchange.getResponse().setStatusCode(errorResponse.status);
      exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
      DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();

      try {
         byte[] bytes = objectMapper.writeValueAsBytes(errorResponse.body);
         return exchange.getResponse().writeWith(Mono.just(bufferFactory.wrap(bytes)));
      } catch (Exception e) {
         return exchange.getResponse().setComplete();
      }
   }

   private ErrorResponse mapExceptionToErrorResponse(final Throwable ex) {
      return switch (ex) {
         case GenericBadRequestException e -> new ErrorResponse(HttpStatus.BAD_REQUEST, e.getError());
         case GenericNotFoundException e -> new ErrorResponse(HttpStatus.NOT_FOUND, e.getError());
         case ServiceUnavailableException e -> new ErrorResponse(HttpStatus.SERVICE_UNAVAILABLE, e.getError());
         case null, default -> new ErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR, null);
      };
   }

   private record ErrorResponse(HttpStatus status, com.reactive.nequi.model.ErrorResponse body) {}
}
