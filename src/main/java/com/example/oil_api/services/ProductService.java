package com.example.oil_api.services;

import com.example.oil_api.mappers.ProductMapper;
import com.example.oil_api.models.command.create.CreateProductCommand;
import com.example.oil_api.models.dto.ProductDto;
import com.example.oil_api.models.entities.Product;
import com.example.oil_api.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Transactional
    public ProductDto create(CreateProductCommand command) {
        Product product = productMapper.mapToEntity(command);
        return productMapper.mapToDto(productRepository.save(product));
    }
}
