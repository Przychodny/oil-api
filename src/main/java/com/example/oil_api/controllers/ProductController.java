package com.example.oil_api.controllers;

import com.example.oil_api.models.command.create.CreateProductCommand;
import com.example.oil_api.models.dto.ProductDto;
import com.example.oil_api.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping
    public ProductDto create(@RequestBody CreateProductCommand command) {
        return productService.create(command);
    }
}
