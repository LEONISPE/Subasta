package com.Subasta_Online.Subasta_historial.Model;

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
public class HistorialPuja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private EstadoSubasta estadoSubasta;
    private String idProducto;
    private Categoria categoria;
    private String descripcion;
    private BigDecimal precioInicial;
    private LocalDateTime horaInicio;
    private EstadoProducto estadoProducto;
    private Duration duracionSubasta;
    private String nombreUsuario;
    private BigDecimal precioActual;
    private String mejorPostor;

}
