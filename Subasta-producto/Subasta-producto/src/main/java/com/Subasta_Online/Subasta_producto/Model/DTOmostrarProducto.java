package com.Subasta_Online.Subasta_producto.Model;

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
