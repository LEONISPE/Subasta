package com.Subasta_Online.Subasta_puja.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DTOpuja {

    private String nombre;
    private float precioInicial;
    private LocalDateTime hora;
    private EstadoSubasta estadoSubasta;
}
