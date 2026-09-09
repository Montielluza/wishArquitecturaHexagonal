package com.wishstore.domain.ports;

import com.wishstore.domain.model.Wishlist;

import java.util.List;
import java.util.Optional;

/* Puerto de salida (Repository Port) que define las operaciones de persistencia
 * necesarias sobre el modelo {@link Wishlist}.
 * <p>Descripcion: Interfaz pura del dominio, sin implementacion en esta capa.</p>
 * <p>Responsabilidad: Declarar el contrato que el dominio necesita para gestionar
 * los registros de la lista de deseos.</p>
 * <p>Dependencias: Unicamente del modelo de dominio {@link Wishlist}.</p>*/
public interface WishlistRepositoryPort {

     /* Obtiene todos los registros de wishlist.
     * @return lista completa de wishlist
     */
    List<Wishlist> findAll();

    /* Busca un registro de wishlist por su identificador.
     * @param id identificador del registro de wishlist
     * @return un {@link Optional} con el registro si existe, vacio en caso contrario*/
    Optional<Wishlist> findById(Long id);

    /* Persiste un registro de wishlist (nuevo o actualizado).
     * @param wishlist registro a guardar
     * @return el registro guardado*/
    Wishlist save(Wishlist wishlist);

    /*Elimina un registro de wishlist por su identificador.
     * @param id identificador del registro a eliminar*/
    void delete(Long id);

    /*Busca el registro de wishlist asociado a un producto especifico.
     * @param productId identificador del producto
     * @return un {@link Optional} con el registro si existe, vacio en caso contrario*/
    Optional<Wishlist> findByProduct(Long productId);
}