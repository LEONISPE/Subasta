package com.Subasta_Online.Subasta_producto.Model;


import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Builder
@AllArgsConstructor
@NoArgsConstructor
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
