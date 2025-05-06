package com.Subasta_Online.Subasta_producto.Service;

import com.Subasta_Online.Subasta_producto.Model.DTOaddProducto;
import com.Subasta_Online.Subasta_producto.Model.DTOmostrarProducto;
import com.Subasta_Online.Subasta_producto.Model.Producto;
import com.Subasta_Online.Subasta_producto.Repository.ProductoRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductoService {

private final ProductoRepository productoRepository;


  public DTOaddProducto addProducto(DTOaddProducto dtOaddProducto) {
      Producto productonew  = new Producto();
      productonew.setNombre(dtOaddProducto.getNombre());
      productonew.setDescripcion(dtOaddProducto.getDescripcion());
      productonew.setEstado(dtOaddProducto.getEstado());
      productonew.setPrecioInicial(dtOaddProducto.getPrecioInicial());
      productonew.setActivo(dtOaddProducto.isActivo());

    productoRepository.save(productonew);
    return  dtOaddProducto;


  }

public List<DTOmostrarProducto> getAllProductosActivos() {
    return productoRepository.findByActivo()
            .stream()
            .map(Producto -> new DTOmostrarProducto(
                    Producto.getNombre(),
                    Producto.getDescripcion(),
                    Producto.getEstado(),
                    Producto.getPrecioInicial()

            )) .toList();


}



}
