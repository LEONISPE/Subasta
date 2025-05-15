package com.Subasta_Online.Subasta_producto.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DTOaddProducto {

    private String nombre;
    private String descripcion;
    private EstadoProducto estadoProducto;
    private Categoria categoria;
}
