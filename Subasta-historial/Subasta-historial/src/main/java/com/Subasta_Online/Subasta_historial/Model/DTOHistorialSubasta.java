package com.Subasta_Online.Subasta_historial.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class DTOHistorialSubasta {

    private Long id;
    private String nombre;
    private float precioInicial;
    private LocalDateTime hora;
    private EstadoSubasta estadoSubasta;
    private float precioFinal;
}
