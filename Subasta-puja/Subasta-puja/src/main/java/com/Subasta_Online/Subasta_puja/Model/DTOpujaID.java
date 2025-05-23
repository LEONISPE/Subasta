package com.Subasta_Online.Subasta_puja.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DTOpujaID {
    private Long id;
    private String idProducto;
    private String nombre;
    private Categoria categoria;
    private String descripcion;
    private float precioInicial;
    private LocalDateTime HoraInicio = LocalDateTime.now();
    private EstadoSubasta estadoSubasta;
    private EstadoProducto estadoProducto;
    private Duration duracionSubasta;

}
