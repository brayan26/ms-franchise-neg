package com.reactive.nequi.server;

import com.reactive.nequi.exceptions.GenericBadRequestException;
import com.reactive.nequi.exceptions.GenericNotFoundException;
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

import java.util.Map;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
@Slf4j
public final class GlobalExceptionHandler implements ErrorWebExceptionHandler {
   private final ObjectMapper objectMapper;

   public GlobalExceptionHandler(ObjectMapper objectMapper) {
      this.objectMapper = objectMapper;
   }

   @Override
   public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
      HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;
      log.error("{}", ex.getLocalizedMessage());
      Object errorDetail = Map.of("error", "UNKNOW_ERROR_DESCRIPTION");

      if (ex instanceof GenericNotFoundException e) {
         errorDetail = e.getError();
         status = HttpStatus.NOT_FOUND;
      }

      if (ex instanceof GenericBadRequestException e) {
         errorDetail = e.getError();
         status = HttpStatus.BAD_REQUEST;
      }

      exchange.getResponse().setStatusCode(status);
      exchange.getResponse().getHeaders().setContentType(MediaType.APPLICATION_JSON);
      DataBufferFactory bufferFactory = exchange.getResponse().bufferFactory();

      try {
         byte[] bytes = objectMapper.writeValueAsBytes(errorDetail);
         return exchange.getResponse().writeWith(Mono.just(bufferFactory.wrap(bytes)));
      } catch (Exception e) {
         return exchange.getResponse().setComplete();
      }
   }
}
