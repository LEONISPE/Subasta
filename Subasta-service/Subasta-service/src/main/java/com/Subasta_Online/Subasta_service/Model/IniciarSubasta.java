package com.Subasta_Online.Subasta_service.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity( name = "Subasta_Iniciada")
@Getter
@Setter
public class IniciarSubasta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private float precioInicial;
    private LocalDateTime hora;
    private EstadoSubasta estadoSubasta;

}
