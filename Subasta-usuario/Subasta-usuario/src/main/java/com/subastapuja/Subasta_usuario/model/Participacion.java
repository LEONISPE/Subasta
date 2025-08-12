package com.subastapuja.Subasta_usuario.model;

import com.subastapuja.Subasta_usuario.model.DTO.ValoracionParticipacion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Participacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mejorPostor;
    private BigDecimal precioActual;
    @Enumerated(EnumType.STRING)
    private ValoracionParticipacion valoracionParticipacion;
}
