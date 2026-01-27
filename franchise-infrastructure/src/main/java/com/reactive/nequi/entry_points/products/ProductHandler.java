package com.reactive.nequi.entry_points.products;

import com.reactive.nequi.entry_points.products.in.ProductRequestDto;
import com.reactive.nequi.mappers.ProductMapper;
import com.reactive.nequi.model.BranchProductView;
import com.reactive.nequi.usecases.products.*;
import com.reactive.nequi.utils.DtoValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.reactive.nequi.utils.GetPathVariableUtil.getId;


@Component
public record ProductHandler(ProductCreatorUseCase productCreatorUseCase, ProductUpdaterUseCase productUpdaterUseCase,
                             ProductLocatorByIdUseCase productLocatorByIdUseCase,
                             ProductFindTopProductByBranchUseCase productFindTopProductByBranchUseCase,
                             ProductEraserUseCase productEraserUseCase, DtoValidator validator, ProductMapper mapper) {

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(ProductRequestDto.class)
                .doOnNext(validator::validate)
                .flatMap(dto -> productCreatorUseCase.execute(this.mapper.toDomain(dto)))
                .flatMap(response -> ServerResponse.status(HttpStatus.CREATED)
                        .bodyValue(this.mapper.toResponseDto(response)));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        return request.bodyToMono(ProductRequestDto.class)
                .doOnNext(validator::validate)
                .flatMap(dto -> productUpdaterUseCase.execute(this.mapper.toCommand(getId(request), dto)))
                .flatMap(response -> ServerResponse.status(HttpStatus.OK)
                        .bodyValue(this.mapper.toResponseDto(response)));
    }

    public Mono<ServerResponse> findById(ServerRequest request) {
        return productLocatorByIdUseCase.execute(getId(request))
                .flatMap(response -> ServerResponse.status(HttpStatus.OK)
                        .bodyValue(this.mapper.toResponseDto(response)));
    }

    public Mono<ServerResponse> findTopProductByBranch(ServerRequest request) {
        Flux<BranchProductView> response = productFindTopProductByBranchUseCase.execute(getId(request, "franchiseId"));
        return ServerResponse.status(HttpStatus.OK)
                .body(response, BranchProductView.class);
    }

    public Mono<ServerResponse> delete(ServerRequest request) {
        return productEraserUseCase.execute(getId(request)).then(ServerResponse.noContent().build());
    }
}
