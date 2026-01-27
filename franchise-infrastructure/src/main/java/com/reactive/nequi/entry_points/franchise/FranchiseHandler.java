package com.reactive.nequi.entry_points.franchise;

import com.reactive.nequi.entry_points.franchise.in.FranchiseRequestDto;
import com.reactive.nequi.mappers.IFranchiseMapper;
import com.reactive.nequi.model.Franchise;
import com.reactive.nequi.usecases.franchise.FranchiseCreatorUseCase;
import com.reactive.nequi.usecases.franchise.FranchiseFinderUseCase;
import com.reactive.nequi.usecases.franchise.FranchiseGetterByIdUseCase;
import com.reactive.nequi.usecases.franchise.FranchiseNameUpdateUseCase;
import com.reactive.nequi.utils.DtoValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import static com.reactive.nequi.utils.GetPathVariableUtil.getId;

@Component
public record FranchiseHandler(DtoValidator validator, IFranchiseMapper mapper,
                               FranchiseCreatorUseCase franchiseCreatorUseCase,
                               FranchiseFinderUseCase franchiseFinderUseCase,
                               FranchiseGetterByIdUseCase franchiseGetterByIdUseCase,
                               FranchiseNameUpdateUseCase franchiseNameUpdateUseCase) {

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(FranchiseRequestDto.class)
                .doOnNext(validator::validate)
                .flatMap(dto -> this.franchiseCreatorUseCase.execute(this.mapper.toDomain(dto)))
                .flatMap(response -> ServerResponse
                        .status(HttpStatus.CREATED)
                        .bodyValue(response)
                );
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        return request.bodyToMono(FranchiseRequestDto.class)
                .doOnNext(validator::validate)
                .flatMap(dto -> franchiseNameUpdateUseCase.execute(mapper.toCommand(getId(request), dto)))
                .flatMap(response -> ServerResponse
                        .status(HttpStatus.OK)
                        .bodyValue(response)
                );
    }

    public Mono<ServerResponse> findOne(ServerRequest request) {
        return this.franchiseGetterByIdUseCase.execute(getId(request))
                .flatMap(response -> ServerResponse
                        .status(HttpStatus.OK)
                        .bodyValue(response)
                );
    }

    public Mono<ServerResponse> findAll(ServerRequest request) {
        Flux<Franchise> response = this.franchiseFinderUseCase.execute();

        return ServerResponse
                .ok()
                .body(response, Franchise.class);

    }
}
