package com.wishstore.infrastructure.adapters.input.rest.controller;

import com.wishstore.application.dto.WishlistRequest;
import com.wishstore.application.dto.WishlistResponse;
import com.wishstore.application.usecase.wishlist.CreateWishlistUseCase;
import com.wishstore.application.usecase.wishlist.DeleteWishlistUseCase;
import com.wishstore.application.usecase.wishlist.GetWishlistUseCase;
import com.wishstore.application.usecase.wishlist.UpdateWishlistUseCase;
import com.wishstore.infrastructure.adapters.input.rest.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
@RequiredArgsConstructor
@Tag(name = "Wishlist", description = "Gestión de la lista de deseos")
public class WishlistController {

    private final CreateWishlistUseCase createWishlistUseCase;
    private final UpdateWishlistUseCase updateWishlistUseCase;
    private final DeleteWishlistUseCase deleteWishlistUseCase;
    private final GetWishlistUseCase getWishlistUseCase;

    @GetMapping
    @Operation(summary = "Listar la wishlist con datos de producto y stock")
    public ResponseEntity<ApiResponse<List<WishlistResponse>>> getWishlist() {
        List<WishlistResponse> wishlist = getWishlistUseCase.execute();
        return ResponseEntity.ok(ApiResponse.of(200, "Wishlist obtenida correctamente", wishlist));
    }

    @PostMapping
    @Operation(summary = "Agregar un producto a la wishlist")
    public ResponseEntity<ApiResponse<WishlistResponse>> createWishlist(@Valid @RequestBody WishlistRequest request) {
        WishlistResponse created = createWishlistUseCase.execute(request);
        return ResponseEntity.status(201)
                .body(ApiResponse.of(201, "Producto agregado a la wishlist", created));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar un registro existente de la wishlist")
    public ResponseEntity<ApiResponse<WishlistResponse>> updateWishlist(@PathVariable Long id,
                                                                        @Valid @RequestBody WishlistRequest request) {
        WishlistResponse updated = updateWishlistUseCase.execute(id, request);
        return ResponseEntity.ok(ApiResponse.of(200, "Wishlist actualizada correctamente", updated));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar un registro de la wishlist")
    public ResponseEntity<ApiResponse<Void>> deleteWishlist(@PathVariable Long id) {
        deleteWishlistUseCase.execute(id);
        return ResponseEntity.ok(ApiResponse.of(200, "Producto eliminado de la wishlist", null));
    }
}