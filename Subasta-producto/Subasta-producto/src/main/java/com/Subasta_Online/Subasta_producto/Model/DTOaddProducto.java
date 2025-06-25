package com.Subasta_Online.Subasta_producto.Model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DTOaddProducto {

    @NotBlank( message = "el nombre de producto es obligatorio")
    private String nombre;
    @NotBlank(message = "la descripcion por motivos de informacion del producto es obligatorio")
    private String descripcion;
    @NotNull(message = "debe selecionar en que estado esta el producto")
    private EstadoProducto estadoProducto;
    @NotNull(message = "debe selecionar la categoria de ese producto")
    private Categoria categoria;
}
