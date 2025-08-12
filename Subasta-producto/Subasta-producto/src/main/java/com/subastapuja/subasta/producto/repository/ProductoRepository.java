package com.subastapuja.subasta.producto.repository;

import com.subastapuja.subasta.producto.model.Categoria;
import com.subastapuja.subasta.producto.model.EstadoProducto;
import com.subastapuja.subasta.producto.model.Producto;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ProductoRepository  extends MongoRepository<Producto, String> {
    Optional<Producto> findById(String id);
    List<Producto> findByNombre(String nombre);
    List<Producto> findByEstadoProducto(EstadoProducto estadoProducto);
    List<Producto> findByCategoria(Categoria categoria);
}
