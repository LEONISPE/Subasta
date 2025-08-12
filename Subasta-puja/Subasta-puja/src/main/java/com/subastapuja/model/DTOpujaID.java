package com.subastapuja.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
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
    private BigDecimal precioInicial;
    private LocalDateTime horaInicio = LocalDateTime.now();
    private EstadoSubasta estadoSubasta;
    private EstadoProducto estadoProducto;
    private Duration duracionSubasta;

}
