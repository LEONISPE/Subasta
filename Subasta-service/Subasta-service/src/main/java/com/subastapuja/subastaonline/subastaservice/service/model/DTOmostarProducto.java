package com.subastapuja.subastaonline.subastaservice.service.model;

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
