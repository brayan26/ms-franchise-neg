package com.reactive.nequi.mappers;

import com.reactive.nequi.command.BranchUpdateCommand;
import com.reactive.nequi.entry_points.branch.in.BranchRequestDto;
import com.reactive.nequi.entry_points.branch.out.BranchResponseDto;
import com.reactive.nequi.model.Branch;
import com.reactive.nequi.persistence.entities.BranchEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BranchMapper {
   Branch toDomain(BranchRequestDto dto);

   BranchEntity toEntity(Branch domain);

   @Mapping(source = "document.id", target = "id")
   @Mapping(source = "document.name", target = "name")
   @Mapping(source = "document.franchiseId", target = "franchiseId")
   @Mapping(source = "document.address", target = "address")
   @Mapping(source = "document.createdAt", target = "createdAt")
   @Mapping(source = "document.updatedAt", target = "updatedAt")
   Branch toDomain(BranchEntity document);

   @Mapping(source = "id", target = "id")
   @Mapping(source = "dto.name", target = "branch.name")
   @Mapping(source = "dto.franchiseId", target = "branch.franchiseId")
   @Mapping(source = "dto.address", target = "branch.address")
   BranchUpdateCommand toCommand(Long id, BranchRequestDto dto);

   BranchResponseDto toResponseDto(Branch domain);
}
