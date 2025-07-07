package com.Subasta_Online.Subasta_service.Excepcions;

public class PeticionInvalidoException extends RuntimeException {
    public PeticionInvalidoException(String mensaje) {
        super(mensaje);
    }
}