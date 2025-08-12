package com.subastapuja.subasta.producto.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DTOmostrarProducto {

    private String id;
    private String nombre;
    private String descripcion;
    private EstadoProducto estadoProducto;
     private Categoria categoria;

}
