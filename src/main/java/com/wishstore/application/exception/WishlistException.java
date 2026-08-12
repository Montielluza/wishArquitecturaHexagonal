package com.wishstore.application.exception;

public class WishlistException extends BusinessException {

    /*onstruye la excepcion con un mensaje descriptivo.
     * @param message detalle legible del error de negocio*/
    public WishlistException(String message) {
        super(message);
    }

    /*Construye la excepcion indicando el identificador de la wishlist afectada.
     * @param wishlistId identificador del registro de wishlist involucrado
     * @param reason     motivo especifico del error*/
    public WishlistException(Long wishlistId, String reason) {
        super("Error en wishlist con id " + wishlistId + ": " + reason);
    }
}