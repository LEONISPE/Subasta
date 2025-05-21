package com.Subasta_Online.Subasta_producto.Repository;

import com.Subasta_Online.Subasta_producto.Model.Categoria;
import com.Subasta_Online.Subasta_producto.Model.EstadoProducto;
import com.Subasta_Online.Subasta_producto.Model.Producto;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository  extends MongoRepository<Producto, String> {
    Optional<Producto> findById(String id);
    List<Producto> findByNombre(String nombre);
    List<Producto> findByEstadoProducto(EstadoProducto estadoProducto);
    List<Producto> findByCategoria(Categoria categoria);
}
