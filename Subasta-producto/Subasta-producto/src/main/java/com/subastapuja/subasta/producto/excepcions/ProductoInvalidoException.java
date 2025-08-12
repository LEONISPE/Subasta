package com.subastapuja.subasta.producto.excepcions;

public class ProductoInvalidoException extends RuntimeException {
    public ProductoInvalidoException(String mensaje) {
        super(mensaje);
    }
}