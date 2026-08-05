package com.wishstore.application.dto;

import com.wishstore.domain.model.ActionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/*DTO de salida que representa un registro de historial hacia el cliente.
 * <p>Descripcion: Objeto de transferencia devuelto por {@code GetHistoryUseCase},
 * construido a partir del modelo de dominio {@code WishlistHistory}.</p>
 * <p>Responsabilidad: Exponer la informacion de auditoria de forma legible
 * para el consumidor externo.</p>
 * <p>Dependencias: Del enum de dominio {@link ActionType}, ya que forma parte
 * del contrato de lectura del historial.</p>*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoryResponse {

    /*Identificador unico del registro de historial*/
    private Long id;

    /*Identificador de la wishlist asociada*/
    private Long wishlistId;

    /*Identificador del producto asociado*/
    private Long productId;

    /*Tipo de accion registrada*/
    private ActionType action;

    /*Descripcion legible del evento*/
    private String description;

    /*Fecha y hora en que ocurrio el evento*/
    private LocalDateTime createdAt;
}