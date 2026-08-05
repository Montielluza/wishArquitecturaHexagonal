package com.wishstore.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishlistHistory {

    /*Identificador unico del registro de historial.*/
    private Long id;

    /*Identificador de la wishlist asociada a este evento.*/
    private Long wishlistId;

    /*Identificador del producto asociado a este evento.*/
    private Long productId;

    /*Tipo de accion registrada.*/
    private ActionType action;

    /*Descripcion legible del evento (por ejemplo, "Producto agregado a la wishlist").*/
    private String description;

    /*Fecha y hora en que ocurrio el evento.*/
    private LocalDateTime createdAt;
}