package com.wishstore.application.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/* DTO de entrada para la creacion o actualizacion de un producto.
 * <p>Descripcion: Objeto de transferencia que recibe el Controller desde el
 * cliente (Angular) y que el Use Case traduce a un modelo de dominio {@code Product}.</p>
 * <p>Responsabilidad: Validar la forma de los datos de entrada antes de que
 * lleguen a la logica de negocio.</p>
 * <p>Dependencias: Solo de Jakarta Bean Validation. No depende del dominio ni de JPA.</p>*/
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductRequest {

    /*Nombre del producto. Obligatorio y no puede estar vacio*/
    @NotBlank(message = "El nombre del producto es obligatorio")
    private String name;

    /*Descripcion del producto*/
    private String description;

    /*Precio del producto. Obligatorio y no puede ser negativo*/
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El precio no puede ser negativo")
    private BigDecimal price;

    /*Stock disponible. Obligatorio y no puede ser negativo*/
    @NotNull(message = "El stock es obligatorio")
    @PositiveOrZero(message = "El stock no puede ser negativo")
    private Integer stock;

    /*URL o referencia de la imagen del producto*/
    private String image;
}