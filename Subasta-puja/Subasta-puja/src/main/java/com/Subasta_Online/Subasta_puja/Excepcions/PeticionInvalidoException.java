package com.Subasta_Online.Subasta_puja.Excepcions;

public class PeticionInvalidoException extends RuntimeException {
    public PeticionInvalidoException(String mensaje) {
        super(mensaje);
    }
}