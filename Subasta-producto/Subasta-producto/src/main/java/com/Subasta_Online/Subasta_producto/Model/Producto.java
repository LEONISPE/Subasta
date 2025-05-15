package com.Subasta_Online.Subasta_producto.Model;


import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document( collection = "producto")
public class Producto {

    @Id
    private String id;
    private String nombre;
    private String descripcion;
    private EstadoProducto estadoProducto;
    private Categoria categoria;
}
