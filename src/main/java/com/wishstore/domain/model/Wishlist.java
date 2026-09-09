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
public class Wishlist {

    /*Identificador unico del registro de wishlist.*/
    private Long id;

    /*Identificador del producto asociado.*/
    private Long productId;

    /*Cantidad deseada del producto.*/
    private Integer quantity;

    /*Fecha y hora de creacion del registro*/
    private LocalDateTime createdAt;

    /* Regla de dominio: incrementa la cantidad actual de la wishlist
     * en el valor indicado.
     *
     * @param amount cantidad adicional a sumar*/
    public void increaseQuantity(int amount) {
        this.quantity = (this.quantity == null ? 0 : this.quantity) + amount;
    }
}