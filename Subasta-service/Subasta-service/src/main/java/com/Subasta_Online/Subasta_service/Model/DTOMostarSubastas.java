﻿package com.Subasta_Online.Subasta_service.Model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class DTOMostarSubastas {
    private Long id;
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
