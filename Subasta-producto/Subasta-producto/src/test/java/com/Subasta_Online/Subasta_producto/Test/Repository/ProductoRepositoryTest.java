package com.Subasta_Online.Subasta_producto.Test.Repository;

import com.Subasta_Online.Subasta_producto.Model.Categoria;
import com.Subasta_Online.Subasta_producto.Model.EstadoProducto;
import com.Subasta_Online.Subasta_producto.Model.Producto;
import com.Subasta_Online.Subasta_producto.Repository.ProductoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
public class ProductoRepositoryTest {

    @Autowired
    private  ProductoRepository productoRepository;

    private Producto productoGuardado;

    @BeforeEach
    void setUp() {
        productoRepository.deleteAll();

        Producto producto = Producto.builder()
                .nombre("PS5")
                .descripcion("Consola")
                .estadoProducto(EstadoProducto.NUEVO)
                .categoria(Categoria.ELECTRONICO)
                .build();
        productoGuardado = productoRepository.save(producto);
    }

    @Test
    void testBuscarPorId() {
        Optional<Producto> encontrado = productoRepository.findById(productoGuardado.getId());

        assertTrue(encontrado.isPresent());
        assertEquals("PS5", encontrado.get().getNombre());
        assertEquals("Consola", encontrado.get().getDescripcion());
    }

    @Test
    void testBuscarPorNombre() {
        List<Producto> resultado = productoRepository.findByNombre("PS5");

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("PS5", resultado.get(0).getNombre());
    }
}