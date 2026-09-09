package com.wishstore.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

public class Product {
    /*Identificador unico del producto. */
    private Long id;
    /*Nombre comercial del producto. */
    private String name;

    /*Descripcion detallada del producto. */
    private String description;

    /*Precio unitario del producto. */
    private BigDecimal price;

    /*Cantidad disponible en inventario. */
    private Integer stock;

    /*URL o referencia de la imagen del producto. */
    private String image;

    /*Regla de dominio: determina si el producto tiene stock disponible
     * para una cantidad solicitada.
     * @param requestedQuantity cantidad que se desea reservar/agregar
     * @return true si hay stock suficiente, false en caso contrario*/
    public boolean hasStockFor(int requestedQuantity) {
        return this.stock != null && this.stock >= requestedQuantity;
    }
}