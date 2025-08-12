package com.subastapuja.subasta.historial.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DTOPujaActualizada {

        private String idProducto;
        private BigDecimal precioActual;
        private String mejorPostor;
        private Float precioFinal;
    }

