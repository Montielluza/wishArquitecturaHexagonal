package com.wishstore.domain.ports;

import com.wishstore.domain.model.WishlistHistory;

import java.util.List;

/* Puerto de salida (Repository Port) que define las operaciones de persistencia
 * necesarias sobre el modelo {@link WishlistHistory}.
 * <p>Descripcion: Interfaz pura del dominio, sin implementacion en esta capa.</p>
 * <p>Responsabilidad: Declarar el contrato que el dominio necesita para registrar
 * y consultar el historial de acciones sobre la wishlist.</p>
 * <p>Dependencias: Unicamente del modelo de dominio {@link WishlistHistory}.</p>*/
public interface HistoryRepositoryPort {

    /* Persiste un nuevo registro de historial.
     * @param history registro de historial a guardar
     * @return el registro guardado (con su id asignado)*/
    WishlistHistory save(WishlistHistory history);

    /* Obtiene todos los registros de historial.
     * @return lista completa de historial*/
    List<WishlistHistory> findAll();

    /* Busca los registros de historial asociados a una wishlist especifica.
     * @param wishlistId identificador de la wishlist
     * @return lista de registros de historial de esa wishlist*/
    List<WishlistHistory> findByWishlist(Long wishlistId);

    /* Busca los registros de historial asociados a un producto especifico.
     * @param productId identificador del producto
     * @return lista de registros de historial de ese producto*/
    List<WishlistHistory> findByProduct(Long productId);
}