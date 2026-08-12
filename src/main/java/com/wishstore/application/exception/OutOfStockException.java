package com.wishstore.application.exception;


public class OutOfStockException extends BusinessException {

    /**
     * Construye la excepcion indicando el producto y la cantidad solicitada
     * que no pudo ser satisfecha.
     *
     * @param productId identificador del producto sin stock suficiente
     * @param requested cantidad solicitada que no pudo cubrirse
     */
    public OutOfStockException(Long productId, int requested) {
        super("Stock insuficiente para el producto con id: " + productId
                + ". Cantidad solicitada: " + requested);
    }

    /**
     * Construye la excepcion con un mensaje personalizado.
     *
     * @param message detalle legible del error
     */
    public OutOfStockException(String message) {
        super(message);
    }
}