package com.Subasta_Online.Subasta_puja.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class DTOiniciarSubasta {
    private String nombre;
    private Categoria categoria;
    private String descripcion;
    private float precioInicial;
    private LocalDateTime horaInicio;
    private EstadoProducto estadoProducto;
    private LocalDate DuracionSubasta;

}
