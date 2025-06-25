package com.Subasta_Online.Subasta_producto.Excepcions;

public class ProductoInvalidoException extends RuntimeException {
    public ProductoInvalidoException(String mensaje) {
        super(mensaje);
    }
}