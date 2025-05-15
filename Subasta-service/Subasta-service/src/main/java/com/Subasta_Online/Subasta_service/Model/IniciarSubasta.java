package com.Subasta_Online.Subasta_service.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity( name = "Subasta_Iniciada")
@Getter
@Setter
public class IniciarSubasta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private Categoria categoria;
    private String descripcion;
    private float precioInicial;
    private LocalDateTime horaInicio = LocalDateTime.now();
    private EstadoProducto estadoProducto;
    private LocalDate DuracionSubasta;

}
