package com.reactive.nequi.mappers;

import com.reactive.nequi.command.ProductUpdaterCommand;
import com.reactive.nequi.entry_points.products.in.ProductRequestDto;
import com.reactive.nequi.entry_points.products.out.ProductResponseDto;
import com.reactive.nequi.model.Product;
import com.reactive.nequi.persistence.entities.ProductEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {
   Product toDomain(ProductRequestDto dto);

   ProductEntity toEntity(Product domain);

   @Mapping(source = "document.id", target = "id")
   @Mapping(source = "document.name", target = "name")
   @Mapping(source = "document.stock", target = "stock")
   @Mapping(source = "document.branchId", target = "branchId")
   @Mapping(source = "document.createdAt", target = "createdAt")
   @Mapping(source = "document.updatedAt", target = "updatedAt")
   Product toDomain(ProductEntity document);

   @Mapping(source = "id", target = "id")
   @Mapping(source = "dto.name", target = "product.name")
   @Mapping(source = "dto.stock", target = "product.stock")
   @Mapping(source = "dto.branchId", target = "product.branchId")
   ProductUpdaterCommand toCommand(Long id, ProductRequestDto dto);

   ProductResponseDto toResponseDto(Product domain);

}
