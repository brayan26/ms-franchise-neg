package com.reactive.nequi.mappers;

import com.reactive.nequi.command.FranchiseNamaUpdateCommand;
import com.reactive.nequi.entry_points.franchise.in.FranchiseRequestDto;
import com.reactive.nequi.model.Franchise;
import com.reactive.nequi.persistence.entities.FranchiseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring")
public interface IFranchiseMapper {
    Franchise toDomain(FranchiseRequestDto dto);

    @Mapping(source = "domain.id", target = "id")
    @Mapping(source = "domain.name", target = "name")
    @Mapping(source = "domain.createdAt", target = "createdAt")
    @Mapping(source = "domain.updatedAt", target = "updatedAt")
    FranchiseEntity toEntity(Franchise domain);

    Franchise toDomain(FranchiseEntity document);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "dto.name", target = "name")
    FranchiseNamaUpdateCommand toCommand(Long id, FranchiseRequestDto dto);
}
