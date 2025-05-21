package com.Subasta_Online.Subasta_service.Model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DTOmostarProducto {

    private String id;
    private String nombre;
    private String descripcion;
    private EstadoProducto estadoProducto;
    private Categoria categoria;
}
