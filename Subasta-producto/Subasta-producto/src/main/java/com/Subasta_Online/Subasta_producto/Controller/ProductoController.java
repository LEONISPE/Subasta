package com.Subasta_Online.Subasta_producto.Controller;

import com.Subasta_Online.Subasta_producto.Model.DTOaddProducto;
import com.Subasta_Online.Subasta_producto.Model.DTOmostrarProducto;
import com.Subasta_Online.Subasta_producto.Service.ProductoServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/producto")
@Tag(name = "product,", description = "api para manejar los productos que seran subastados por el usuario")
public class ProductoController {

    private final ProductoServiceImpl productoService;


    public ProductoController(ProductoServiceImpl productoService) {
        this.productoService = productoService;
    }


    @PostMapping("/guardar")
    @Operation(summary = "guardar producto", description = "controlador para gurdar los productos del usuario")
    public ResponseEntity<DTOaddProducto> guardarProducto( @Valid @RequestBody DTOaddProducto dtOaddProducto) {
         DTOaddProducto guardado  =  productoService.addProducto(dtOaddProducto);
         DTOmostrarProducto dtOmostrarProducto = new DTOmostrarProducto();
        URI location = URI.create("/producto/" + dtOmostrarProducto.getId());
        return ResponseEntity.created(location).body(guardado);
    }


    @GetMapping("/mostrar")
    @Operation(summary = "Listar productos", description = "controlador para listar  los productos del usuario")
    public ResponseEntity<List<DTOmostrarProducto>> mostrarProducto() {
          List<DTOmostrarProducto> dtOmostrarProductos =  productoService.getAllProductos();
        return ResponseEntity.ok().body(dtOmostrarProductos);
    }

    @GetMapping("/mostrar/{id}")
    @Operation(summary = "obtener  producto por id ", description = "controlador para obtener  los productos del usuario por su id ")
    public ResponseEntity<DTOmostrarProducto> mostrarProductoPorId(@PathVariable String id) {
        DTOmostrarProducto producto = productoService.getProductoPorId(id);
        return ResponseEntity.ok().body(producto);
    }
}
