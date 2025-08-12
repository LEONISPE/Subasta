package com.subastapuja.subastaonline.subastaservice.service.excepcions;

public class PeticionInvalidoException extends RuntimeException {
    public PeticionInvalidoException(String mensaje) {
        super(mensaje);
    }
    public PeticionInvalidoException(String message, Throwable cause) {
        super(message, cause);
    }
}