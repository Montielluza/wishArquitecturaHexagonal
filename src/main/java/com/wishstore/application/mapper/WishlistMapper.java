package com.wishstore.application.mapper;

import com.wishstore.application.dto.ProductResponse;
import com.wishstore.application.dto.WishlistRequest;
import com.wishstore.application.dto.WishlistResponse;
import com.wishstore.domain.model.Wishlist;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WishlistMapper {

    /* Convierte un DTO de entrada en un modelo de dominio nuevo.
     * <p>El {@code id} y {@code createdAt} no vienen del DTO de entrada:
     * se ignoran deliberadamente porque el Use Case los asigna al persistir.</p>
     * @param request datos de entrada de la wishlist
     * @return modelo de dominio {@link Wishlist} (sin id ni fecha asignados)*/
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    Wishlist toDomain(WishlistRequest request);

    /* Convierte un modelo de dominio de wishlist, junto con el DTO del producto
     * ya resuelto, en el DTO de salida compuesto.
     * <p>Se especifica explicitamente el origen de cada campo porque tanto
     * {@code wishlist} como {@code product} tienen un atributo {@code id},
     * y MapStruct no puede resolver esa ambiguedad por si solo.</p>
     * @param wishlist modelo de dominio de wishlist
     * @param product  DTO de salida del producto asociado (ya calculado por el Use Case)
     * @return DTO de salida {@link WishlistResponse}*/
    @Mapping(target = "id", source = "wishlist.id")
    @Mapping(target = "quantity", source = "wishlist.quantity")
    @Mapping(target = "createdAt", source = "wishlist.createdAt")
    @Mapping(target = "product", source = "product")
    WishlistResponse toResponse(Wishlist wishlist, ProductResponse product);

    /* Actualiza un modelo de dominio existente con los datos de un DTO de entrada,
     * sin sobrescribir el {@code id} ni el {@code createdAt} originales.
     * @param request  datos de entrada con la informacion actualizada
     * @param wishlist modelo de dominio destino que sera modificado*/
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    void update(WishlistRequest request, @MappingTarget Wishlist wishlist);
}