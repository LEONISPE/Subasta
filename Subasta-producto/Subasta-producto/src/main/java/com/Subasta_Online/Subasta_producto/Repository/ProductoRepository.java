package com.Subasta_Online.Subasta_producto.Repository;

import com.Subasta_Online.Subasta_producto.Model.Producto;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductoRepository  extends MongoRepository<Producto, String> {
    List<Producto> findByNombre(String nombre);
    List<Producto> findByEstado(String descripcion);
    List<Producto> findByPrecioInicial(float precioInicial);

    List<Producto> findByActivo();
}
