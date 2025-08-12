package com.subastapuja.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DTOApuntarsePuja {


    @NotBlank(message = "el id del producto debe ser obligatorio")
    private String idProducto;
    private BigDecimal precioActual;
    private String nombreUsuario;
}
