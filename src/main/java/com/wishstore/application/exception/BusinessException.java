package com.wishstore.application.exception;

public abstract class BusinessException extends RuntimeException {

    /*Construye la excepcion con un mensaje descriptivo.
     * @param message detalle legible del error de negocio*/
    protected BusinessException(String message) {
        super(message);
    }

    /*Construye la excepcion con un mensaje descriptivo y una causa raiz.
     * @param message detalle legible del error de negocio
     * @param cause   causa original de la excepcion*/
    protected BusinessException(String message, Throwable cause) {
        super(message, cause);
    }
}