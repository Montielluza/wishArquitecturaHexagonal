package com.wishstore.application.exception;


public class ProductNotFoundException extends BusinessException {

    /*Construye la excepcion indicando el identificador del producto no encontrado.
     * @param productId identificador del producto que no fue encontrado
     */
    public ProductNotFoundException(Long productId) {
        super("No se encontro el producto con id: " + productId);
    }

    /*Construye la excepcion con un mensaje personalizado.
     *
     * @param message detalle legible del error
     */
    public ProductNotFoundException(String message) {
        super(message);
    }
}