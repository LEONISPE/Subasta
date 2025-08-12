package com.subastapuja.subasta.historial.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DTOiniciarSubasta {
    private String idProducto;
    private String nombre;
    private Categoria categoria;
    private String descripcion;
    private BigDecimal precioInicial;
    private LocalDateTime horaInicio;
    private EstadoProducto estadoProducto;
    private Duration duracionSubasta;
    private String nombreUsuario;
}
