package com.Subasta_Online.Subasta_producto.Controller;

import com.Subasta_Online.Subasta_producto.Model.DTOaddProducto;
import com.Subasta_Online.Subasta_producto.Model.DTOmostrarProducto;
import com.Subasta_Online.Subasta_producto.Service.ProductoServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    private final ProductoServiceImpl productoService;


    public ProductoController(ProductoServiceImpl productoService) {
        this.productoService = productoService;
    }

    @PostMapping("/guardar")
    public ResponseEntity<DTOaddProducto> guardarProducto( @Valid @RequestBody DTOaddProducto dtOaddProducto) {
         DTOaddProducto guardado  =  productoService.addProducto(dtOaddProducto);
         DTOmostrarProducto dtOmostrarProducto = new DTOmostrarProducto();
        URI location = URI.create("/producto/" + dtOmostrarProducto.getId());
        return ResponseEntity.created(location).body(guardado);
    }


    @GetMapping("/mostrar")
    public ResponseEntity<List<DTOmostrarProducto>> mostrarProducto() {
          List<DTOmostrarProducto> dtOmostrarProductos =  productoService.getAllProductos();
        return ResponseEntity.ok().body(dtOmostrarProductos);
    }

    @GetMapping("/mostrar/{id}")
    public ResponseEntity<DTOmostrarProducto> mostrarProductoPorId(@PathVariable String id) {
        DTOmostrarProducto producto = productoService.getProductoPorId(id);
        return ResponseEntity.ok().body(producto);
    }
}
