package com.wishstore.infrastructure.adapters.input.rest.controller;

import com.wishstore.application.dto.ProductResponse;
import com.wishstore.application.usecase.product.GetProductByIdUseCase;
import com.wishstore.application.usecase.product.GetProductsUseCase;
import com.wishstore.infrastructure.adapters.input.rest.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Tag(name = "Products", description = "Catálogo de productos")
public class ProductController {

    private final GetProductsUseCase getProductsUseCase;
    private final GetProductByIdUseCase getProductByIdUseCase;

    @GetMapping
    @Operation(summary = "Listar todos los productos del catálogo")
    public ResponseEntity<ApiResponse<List<ProductResponse>>> getAllProducts() {
        List<ProductResponse> products = getProductsUseCase.execute();
        return ResponseEntity.ok(ApiResponse.of(200, "Productos obtenidos correctamente", products));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener un producto por id")
    public ResponseEntity<ApiResponse<ProductResponse>> getProductById(@PathVariable Long id) {
        ProductResponse product = getProductByIdUseCase.execute(id);
        return ResponseEntity.ok(ApiResponse.of(200, "Producto obtenido correctamente", product));
    }
}