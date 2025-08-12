package com.subastapuja.subasta.producto.service;

import com.subastapuja.subasta.producto.model.DTOaddProducto;
import com.subastapuja.subasta.producto.model.DTOmostrarProducto;

import java.util.List;

public interface ProductoService {

    DTOaddProducto addProducto(DTOaddProducto dtOaddProducto);

    List<DTOmostrarProducto> getAllProductos();

    DTOmostrarProducto getProductoPorId(String id);
}
