package com.subastapuja.Subasta_usuario.model.DTO;

public enum ValoracionParticipacion {
    A1(10, 100),
    B2(100, 1000),
    C3(1000, 10000),
    D4(10000, 100000),
    F5(100000, 1000000);

    private final double minimo;
    private final double maximo;

    ValoracionParticipacion(double minimo, double maximo) {
        this.minimo = minimo;
        this.maximo = maximo;
    }

    public static ValoracionParticipacion clasificarPorMonto(double monto) {
        for (ValoracionParticipacion valoracion : values()) {
            if (monto >= valoracion.minimo && monto < valoracion.maximo) {
                return valoracion;
            }
        }
        throw new IllegalArgumentException("Monto fuera de rango: " + monto);
    }

    public double getMinimo() {
        return minimo;
    }

    public double getMaximo() {
        return maximo;
    }
}
