package com.wishstore.application.mapper;

import com.wishstore.application.dto.ProductRequest;
import com.wishstore.application.dto.ProductResponse;
import com.wishstore.domain.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ProductMapper {

    /* Convierte un DTO de entrada en un modelo de dominio nuevo.
     * <p>El {@code id} se ignora deliberadamente: lo asigna la persistencia
     * al guardar un producto nuevo.</p>
     * @param request datos de entrada del producto
     * @return modelo de dominio {@link Product} (sin id asignado)*/
    @Mapping(target = "id", ignore = true)
    Product toDomain(ProductRequest request);

    /* Convierte un modelo de dominio en su DTO de salida.
     * <p>El campo {@code outOfStock} se ignora aqui deliberadamente: no
     * proviene del dominio de forma directa, sino que el Use Case lo calcula
     * y lo asigna despues de invocar este metodo (regla de negocio, no de mapeo).</p>
     * @param product modelo de dominio a convertir
     * @return DTO de salida {@link ProductResponse}*/
    @Mapping(target = "outOfStock", ignore = true)
    ProductResponse toResponse(Product product);

    /* Actualiza un modelo de dominio existente con los datos de un DTO de entrada,
     * sin sobrescribir el {@code id} original.
     * @param request datos de entrada con la informacion actualizada
     * @param product modelo de dominio destino que sera modificado*/
    @Mapping(target = "id", ignore = true)
    void update(ProductRequest request, @MappingTarget Product product);
}