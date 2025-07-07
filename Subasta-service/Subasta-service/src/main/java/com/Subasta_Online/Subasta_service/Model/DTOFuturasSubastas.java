package com.Subasta_Online.Subasta_service.Model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

    @NotBlank(message = "el id del producto no debe ser nulo")
    private String idProducto;
    @NotBlank(message = "el nombre del producto no debe estar en blanco")
    private String nombre;
    @NotNull(message = "debe selecionar una categoria")
    private Categoria categoria;
    @NotBlank(message = "la descripcion no debe estar en blanco")
    private String descripcion;
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false , message = "el precio inicial debe ser mayor a 0")
    private BigDecimal precioInicial;
    @NotNull(message = "el estado del producto no debe estar nulo")
    private EstadoProducto estadoProducto;
    private Duration duracionSubasta;
    private String nombreUsuario;
    private LocalDateTime fechaFuturaInicio;
}
