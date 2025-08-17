package com.subastapuja.subasta.producto.config;


import com.subastapuja.subasta.producto.model.DTOComentarios;
import com.subastapuja.subasta.producto.service.ProductoServiceImpl;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ConsumerProducto {


    private final ProductoServiceImpl productoService;
    private static final Logger logger = LoggerFactory.getLogger(ConsumerProducto.class);


    @KafkaListener(
            topics = "Comentarios",
            groupId = "Producto-group",
            containerFactory = "ProductoListenerContainerFactory"
    )
    public void consumirComentarios(DTOComentarios dtoComentarios) {
        logger.info("comentarios recibido {}", dtoComentarios);
        productoService.guardarcomentariosdesdePuja(dtoComentarios);

    }
}
