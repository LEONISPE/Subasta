package com.subastapuja.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Puja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    private BigDecimal precioActual;
    private String nombreUsuario;
    private String mejorPostor;
    private LocalDateTime fechaFuturaInicio;

}
