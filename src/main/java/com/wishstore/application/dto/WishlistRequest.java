package com.wishstore.application.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/*DTO de entrada para la creacion o actualizacion de un registro de wishlist.
 * <p>Descripcion: Objeto de transferencia recibido por el Controller desde el
 * cliente, traducido por el Use Case al modelo de dominio {@code Wishlist}.</p>
 * <p>Responsabilidad: Validar los datos minimos necesarios para agregar o
 * actualizar un producto en la wishlist.</p>
 * <p>Dependencias: Solo de Jakarta Bean Validation.</p>*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishlistRequest {

    /*Identificador del producto a agregar/actualizar en la wishlist. Obligatorio*/
    @NotNull(message = "El identificador del producto es obligatorio")
    private Long productId;

    /*Cantidad deseada. Obligatoria y debe ser al menos 1*/
    @NotNull(message = "La cantidad es obligatoria")
    @Min(value = 1, message = "La cantidad debe ser al menos 1")
    private Integer quantity;
}