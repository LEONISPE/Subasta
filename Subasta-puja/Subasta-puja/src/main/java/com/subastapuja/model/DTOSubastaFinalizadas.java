package com.subastapuja.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DTOSubastaFinalizadas {

    private String idProducto;
    private EstadoSubasta estadoSubasta;
}
