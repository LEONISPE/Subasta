package com.Subasta_Online.Subasta_service.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Entity( name = "Subasta_Iniciada")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class IniciarSubasta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Categoria categoria;
    private String descripcion;
    private BigDecimal precioInicial;
    private LocalDateTime horaInicio = LocalDateTime.now();
    private EstadoProducto estadoProducto;
    private Duration duracionSubasta;


}
