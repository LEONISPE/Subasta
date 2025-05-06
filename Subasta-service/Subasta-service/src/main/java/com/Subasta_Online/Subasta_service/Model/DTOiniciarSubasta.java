package com.Subasta_Online.Subasta_service.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DTOiniciarSubasta {

    private Long id;
    private String nombre;
    private float precioInicial;
}
