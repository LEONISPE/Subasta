package com.Subasta_Online.Subasta_service.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DTOFuturasSubastas {

    private String idProducto;  // <--- cambio aquí
    private String nombre;
    private Categoria categoria;
    private String descripcion;
    private BigDecimal precioInicial;
    private EstadoProducto estadoProducto;
    private Duration duracionSubasta;
    private String nombreUsuario;
    private LocalDateTime fechaFuturaInicio;
}
