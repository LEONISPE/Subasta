package com.Subasta_Online.Subasta_producto.Test.Service;

import com.Subasta_Online.Subasta_producto.Model.*;
import com.Subasta_Online.Subasta_producto.Repository.ProductoRepository;
import com.Subasta_Online.Subasta_producto.Service.ProductoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class ProductoServiceTest {

    private ProductoRepository productoRepository;
    private ProductoServiceImpl productoService;

    @BeforeEach
    void setUp() {
        productoRepository = mock(ProductoRepository.class);
        productoService = new ProductoServiceImpl(productoRepository);
    }

    @Test
    void testAddProducto() {
        DTOaddProducto dto = new DTOaddProducto("PS5", "Consola", EstadoProducto.NUEVO, Categoria.ELECTRONICO);

        when(productoRepository.save(any(Producto.class))).thenAnswer(invocation -> {
            Producto p = invocation.getArgument(0);
            p.setId("1"); // simular que Mongo asigna ID
            return p;
        });

        DTOaddProducto result = productoService.addProducto(dto);
        assertEquals("PS5", result.getNombre());
        verify(productoRepository, times(1)).save(any(Producto.class));
    }

    @Test
    void testGetAllProductos() {
        Producto producto = Producto.builder()
                .id("1")
                .nombre("PS5")
                .descripcion("Consola")
                .estadoProducto(EstadoProducto.NUEVO)
                .categoria(Categoria.ELECTRONICO)
                .build();

        when(productoRepository.findAll()).thenReturn(List.of(producto));

        List<DTOmostrarProducto> result = productoService.getAllProductos();

        assertEquals(1, result.size());
        assertEquals("PS5", result.get(0).getNombre());
    }

    @Test
    void testGetProductoPorId() {
        Producto producto = Producto.builder()
                .id("1")
                .nombre("PS5")
                .descripcion("Consola")
                .estadoProducto(EstadoProducto.NUEVO)
                .categoria(Categoria.ELECTRONICO)
                .build();

        when(productoRepository.findById("1")).thenReturn(Optional.of(producto));

        DTOmostrarProducto result = productoService.getProductoPorId("1");

        assertEquals("PS5", result.getNombre());
        assertEquals("Consola", result.getDescripcion());
    }
}