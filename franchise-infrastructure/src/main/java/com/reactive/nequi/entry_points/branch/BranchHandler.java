package com.reactive.nequi.entry_points.branch;

import com.reactive.nequi.entry_points.branch.in.BranchRequestDto;
import com.reactive.nequi.entry_points.branch.out.BranchResponseDto;
import com.reactive.nequi.mappers.BranchMapper;
import com.reactive.nequi.usecases.branch.BranchCreatorUseCase;
import com.reactive.nequi.usecases.branch.BranchLocatorByFranchiseIdUseCase;
import com.reactive.nequi.usecases.branch.BranchUpdaterUseCase;
import com.reactive.nequi.utils.DtoValidator;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static com.reactive.nequi.utils.GetPathVariableUtil.getId;

@Component
public record BranchHandler(BranchCreatorUseCase branchCreatorUseCase, BranchUpdaterUseCase branchUpdaterUseCase,
                            BranchLocatorByFranchiseIdUseCase branchLocatorByFranchiseIdUseCase, BranchMapper mapper,
                            DtoValidator validator) {

    public Mono<ServerResponse> create(ServerRequest request) {
        return request.bodyToMono(BranchRequestDto.class)
                .doOnNext(validator::validate)
                .flatMap(dto -> branchCreatorUseCase.execute(mapper.toDomain(dto)))
                .flatMap(response -> ServerResponse.status(HttpStatus.CREATED)
                        .bodyValue(mapper.toResponseDto(response)));
    }

    public Mono<ServerResponse> update(ServerRequest request) {
        return request.bodyToMono(BranchRequestDto.class)
                .doOnNext(validator::validate)
                .flatMap(dto -> branchUpdaterUseCase.execute(mapper.toCommand(getId(request), dto)))
                .flatMap(response -> ServerResponse.status(HttpStatus.OK)
                        .bodyValue(mapper.toResponseDto(response))
                );
    }

    public Mono<ServerResponse> findByFranchiseId(ServerRequest request) {
        Flux<BranchResponseDto> response = branchLocatorByFranchiseIdUseCase.execute(getId(request, "franchiseId"))
                .map(mapper::toResponseDto);

        return ServerResponse.ok().body(response, BranchResponseDto.class);

    }

}
