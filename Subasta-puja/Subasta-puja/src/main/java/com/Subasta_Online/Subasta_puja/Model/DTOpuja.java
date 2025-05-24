package com.Subasta_Online.Subasta_puja.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DTOpuja {
    private String idProducto;
    private String nombre;
    private Categoria categoria;
    private String descripcion;
    private BigDecimal precioInicial;
    private LocalDateTime HoraInicio;
    private EstadoSubasta estadoSubasta;
    private EstadoProducto estadoProducto;
    private Duration duracionSubasta;
    private BigDecimal precioActual;
}
