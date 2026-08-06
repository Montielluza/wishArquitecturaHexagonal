package com.wishstore.domain.ports;

import com.wishstore.domain.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepositoryPort {

    /* Obtiene todos los productos del catalogo.
     * @return lista de productos disponibles*/
    List<Product> findAll();

    /* Busca un producto por su identificador.
     * @param id identificador del producto
     * @return un {@link Optional} con el producto si existe, vacio en caso contrario*/
    Optional<Product> findById(Long id);

    /* Persiste un nuevo producto.
     * @param product producto a guardar
     * @return el producto guardado (con su id asignado)*/
    Product save(Product product);

    /* Verifica si existe un producto con el identificador dado.
     * @param id identificador del producto
     * @return true si existe, false en caso contrario*/
    boolean exists(Long id);

    /* Actualiza los datos de un producto existente.
     * @param product producto con los datos actualizados
     * @return el producto actualizado*/
    Product update(Product product);

    /* Elimina un producto por su identificador.
     * @param id identificador del producto a eliminar*/
    void delete(Long id);
}
