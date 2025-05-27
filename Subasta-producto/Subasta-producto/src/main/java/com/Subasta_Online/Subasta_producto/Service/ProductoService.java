package com.Subasta_Online.Subasta_producto.Service;

import com.Subasta_Online.Subasta_producto.Model.DTOaddProducto;
import com.Subasta_Online.Subasta_producto.Model.DTOmostrarProducto;

import java.util.List;

public interface ProductoService {

    DTOaddProducto addProducto(DTOaddProducto dtOaddProducto);

    List<DTOmostrarProducto> getAllProductos();

    DTOmostrarProducto getProductoPorId(String id);
}
