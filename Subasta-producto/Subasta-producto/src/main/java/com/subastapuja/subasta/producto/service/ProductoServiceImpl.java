package com.subastapuja.subasta.producto.service;

import com.subastapuja.subasta.producto.excepcions.ProductoInvalidoException;
import com.subastapuja.subasta.producto.model.DTOaddProducto;
import com.subastapuja.subasta.producto.model.DTOmostrarProducto;
import com.subastapuja.subasta.producto.model.Producto;
import com.subastapuja.subasta.producto.repository.ProductoRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductoServiceImpl  implements ProductoService{

    private final ProductoRepository productoRepository;

@Override
    public DTOaddProducto addProducto(DTOaddProducto dtOaddProducto) {
    if (dtOaddProducto.getNombre().contains("xxx")) {
        throw new ProductoInvalidoException("Nombre de producto no permitido");
    }
        Producto productonew = new Producto();
        productonew.setNombre(dtOaddProducto.getNombre());
        productonew.setDescripcion(dtOaddProducto.getDescripcion());
        productonew.setEstadoProducto(dtOaddProducto.getEstadoProducto());
        productonew.setCategoria(dtOaddProducto.getCategoria());
        productoRepository.save(productonew);
        return dtOaddProducto;


    }

    @Override
    public List<DTOmostrarProducto> getAllProductos() {
        return productoRepository.findAll()
                .stream()
                .map(producto -> new DTOmostrarProducto(
                        producto.getId(),
                        producto.getNombre(),
                        producto.getDescripcion(),
                        producto.getEstadoProducto(),
                        producto.getCategoria()
                )).toList();


    }

    @Override
    public DTOmostrarProducto getProductoPorId(String id) {
        return productoRepository.findById(id)
                .map(producto -> new DTOmostrarProducto(
                        producto.getId(),
                        producto.getNombre(),
                        producto.getDescripcion(),
                        producto.getEstadoProducto(),
                        producto.getCategoria()
                ))
                .orElseThrow(() -> new ProductoInvalidoException("Producto con ID " + id + " no encontrado"));
    }
    }

