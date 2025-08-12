package com.subastapuja.subastaonline.subastaservice.service.model;

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
public class DTOiniciarSubasta {
    private Long id;
    @NotBlank(message = "el id del producto no debe estar vacio")
    private String idProducto;
    @NotBlank(message = "el nombre del producto debe no debe estar vacio")
    private String nombre;
    @NotNull(message = "debe selecionar una categoria")
    private Categoria categoria;
    @NotBlank(message = "debe tener una descripcion ")
    private String descripcion;
    @NotNull
    @DecimalMin(value = "0.0" , inclusive = false , message = "el precio debe ser mayor a 0")
    private BigDecimal precioInicial;
    private LocalDateTime horaInicio;
    @NotNull(message = "debe selecionar en que estado esta el producto")
    private EstadoProducto estadoProducto;
    private Duration duracionSubasta;
    private String nombreUsuario;
}
