package com.subastapuja.Subasta_usuario.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DTOParticipacionSubasta {
    private String mejorPostor;
    private BigDecimal precioActual;
    private ValoracionParticipacion valoracionParticipacion;
}
