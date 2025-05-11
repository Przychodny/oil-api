package com.example.oil_api.mappers;

import com.example.oil_api.models.command.create.CreateProductCommand;
import com.example.oil_api.models.dto.ProductDto;
import com.example.oil_api.models.entities.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    Product mapToEntity(CreateProductCommand command);

    ProductDto mapToDto(Product product);
}
