package com.Subasta_Online.Subasta_producto.Service;

import com.Subasta_Online.Subasta_producto.Model.DTOaddProducto;
import com.Subasta_Online.Subasta_producto.Model.DTOmostrarProducto;
import com.Subasta_Online.Subasta_producto.Model.Producto;
import com.Subasta_Online.Subasta_producto.Repository.ProductoRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@AllArgsConstructor
public class ProductoService {

    private final ProductoRepository productoRepository;


    public DTOaddProducto addProducto(DTOaddProducto dtOaddProducto) {
        Producto productonew = new Producto();
        productonew.setNombre(dtOaddProducto.getNombre());
        productonew.setDescripcion(dtOaddProducto.getDescripcion());
        productonew.setEstadoProducto(dtOaddProducto.getEstadoProducto());
        productonew.setCategoria(dtOaddProducto.getCategoria());
        productoRepository.save(productonew);
        return dtOaddProducto;


    }

    public List<DTOmostrarProducto> getAllProductos() {
        return productoRepository.findAll()
                .stream()
                .map(Producto -> new DTOmostrarProducto(
                        Producto.getId(),
                        Producto.getNombre(),
                        Producto.getDescripcion(),
                        Producto.getEstadoProducto(),
                        Producto.getCategoria()
                )).toList();


    }

    public DTOmostrarProducto getProductoPorId(String id) {
        return productoRepository.findById(id)
                .map(producto -> new DTOmostrarProducto(
                        producto.getId(),
                        producto.getNombre(),
                        producto.getDescripcion(),
                        producto.getEstadoProducto(),
                        producto.getCategoria()
                ))
                .orElseThrow(() -> new NoSuchElementException("Producto no encontrado con id " + id));
    }
}
