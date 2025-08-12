package com.subastapuja.excepcions;

public class PeticionInvalidoException extends RuntimeException {
    public PeticionInvalidoException(String mensaje) {
        super(mensaje);
    }
}