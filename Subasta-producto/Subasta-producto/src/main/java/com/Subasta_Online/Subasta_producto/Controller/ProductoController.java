package com.Subasta_Online.Subasta_producto.Controller;

import com.Subasta_Online.Subasta_producto.Model.DTOaddProducto;
import com.Subasta_Online.Subasta_producto.Model.DTOmostrarProducto;
import com.Subasta_Online.Subasta_producto.Service.ProductoServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/producto")
public class ProductoController {

    private final ProductoServiceImpl productoService;


    public ProductoController(ProductoServiceImpl productoService) {
        this.productoService = productoService;
    }

    @PostMapping("/guardar")
    public ResponseEntity<DTOaddProducto> guardarProducto(@RequestBody DTOaddProducto dtOaddProducto) {
        productoService.addProducto(dtOaddProducto);
       return ResponseEntity.ok().body(dtOaddProducto);
    }


    @GetMapping("/mostrar")
    public ResponseEntity<List<DTOmostrarProducto>> mostrarProducto() {
        productoService.getAllProductos();
        return ResponseEntity.ok().body(productoService.getAllProductos());
    }

    @GetMapping("/mostrar/{id}")
    public ResponseEntity<DTOmostrarProducto> mostrarProductoPorId(@PathVariable String id) {
        DTOmostrarProducto producto = productoService.getProductoPorId(id);
        return ResponseEntity.ok(producto);
    }
}
