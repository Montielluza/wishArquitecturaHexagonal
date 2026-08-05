package com.wishstore.application.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/*DTO de salida que representa un registro de wishlist hacia el cliente.
 * <p>Descripcion: Objeto de transferencia devuelto por los Use Cases de wishlist,
 * enriquecido con la informacion del producto asociado.</p>
 * <p>Responsabilidad: Exponer el estado de la wishlist junto con datos derivados
 * (por ejemplo, si el producto asociado esta agotado), calculados en el Use Case.</p>
 * <p>Dependencias: Compone un {@link ProductResponse} para representar el producto asociado.</p>*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WishlistResponse {

    /*Identificador unico del registro de wishlist*/
    private Long id;

    /*Cantidad deseada del producto*/
    private Integer quantity;

    /*Fecha y hora de creacion del registro*/
    private LocalDateTime createdAt;

    /*Datos del producto asociado a este registro de wishlist*/
    private ProductResponse product;
}