package com.wishstore.infrastructure.adapters.input.rest.controller;

import com.wishstore.application.dto.HistoryResponse;
import com.wishstore.application.usecase.history.GetHistoryUseCase;
import com.wishstore.infrastructure.adapters.input.rest.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/history")
@RequiredArgsConstructor
@Tag(name = "History", description = "Historial de acciones sobre la wishlist")
public class HistoryController {

    private final GetHistoryUseCase getHistoryUseCase;

    @GetMapping
    @Operation(summary = "Obtener el historial completo de la wishlist")
    public ResponseEntity<ApiResponse<List<HistoryResponse>>> getHistory() {
        List<HistoryResponse> history = getHistoryUseCase.execute();
        return ResponseEntity.ok(ApiResponse.of(200, "Historial obtenido correctamente", history));
    }
}