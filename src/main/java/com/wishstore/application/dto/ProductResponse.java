package com.wishstore.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/*DTO de salida que representa un producto hacia el cliente (Angular).
 * <p>Descripcion: Objeto de transferencia devuelto por los Use Cases de producto,
 * construido a partir del modelo de dominio {@code Product} mediante MapStruct.</p>
 * <p>Responsabilidad: Exponer unicamente los datos de producto relevantes para
 * el consumidor externo, desacoplando el dominio de la representacion HTTP.</p>
 * <p>Dependencias: Ninguna dependencia de framework externo, solo tipos base de Java.</p>*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductResponse {

    /*Identificador unico del producto*/
    private Long id;

    /*Nombre del producto*/
    private String name;

    /*Descripcion del producto*/
    private String description;

    /*Precio del producto*/
    private BigDecimal price;

    /*Stock disponible*/
    private Integer stock;

    /*URL o referencia de la imagen del producto*/
    private String image;

    /*Indica si el producto esta agotado (stock == 0). Calculado por el Use Case*/
    private boolean outOfStock;
}