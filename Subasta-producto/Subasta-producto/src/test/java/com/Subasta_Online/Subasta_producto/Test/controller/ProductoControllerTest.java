package com.Subasta_Online.Subasta_producto.Test.controller;






import com.Subasta_Online.Subasta_producto.Controller.ProductoController;
import com.Subasta_Online.Subasta_producto.Model.Categoria;
import com.Subasta_Online.Subasta_producto.Model.DTOaddProducto;
import com.Subasta_Online.Subasta_producto.Model.DTOmostrarProducto;
import com.Subasta_Online.Subasta_producto.Model.EstadoProducto;
import com.Subasta_Online.Subasta_producto.Service.ProductoServiceImpl;
import com.Subasta_Online.Subasta_producto.Test.TestSecurityConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.any;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProductoController.class)
@Import({ProductoControllerTest.Config.class, TestSecurityConfig.class})
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ProductoServiceImpl productoService;

    @Autowired
    private ObjectMapper objectMapper;

    @TestConfiguration
    static class Config {
        @Bean
        public ProductoServiceImpl productoService() {
            return mock(ProductoServiceImpl.class);
        }
    }

    @Test
    void testGuardarProducto() throws Exception {
        DTOaddProducto dto = new DTOaddProducto("PS5", "Consola", EstadoProducto.NUEVO, Categoria.ELECTRONICO);
        when(productoService.addProducto(any(DTOaddProducto.class))).thenReturn(dto);

        mockMvc.perform(post("/producto/guardar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("PS5"));
    }

    @Test
    void testMostrarProducto() throws Exception {
        DTOmostrarProducto dto = new DTOmostrarProducto("1", "PS5", "Consola", EstadoProducto.NUEVO, Categoria.ELECTRONICO);
        when(productoService.getAllProductos()).thenReturn(List.of(dto));

        mockMvc.perform(get("/producto/mostrar"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nombre").value("PS5"));
    }

    @Test
    void testMostrarProductoPorId() throws Exception {
        DTOmostrarProducto dto = new DTOmostrarProducto("1", "PS5", "Consola", EstadoProducto.NUEVO, Categoria.ELECTRONICO);
        when(productoService.getProductoPorId("1")).thenReturn(dto);

        mockMvc.perform(get("/producto/mostrar/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("PS5"));
    }
}
