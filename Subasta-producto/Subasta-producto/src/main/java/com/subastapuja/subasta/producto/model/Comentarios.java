package com.subastapuja.subasta.producto.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "comentario")
public class Comentarios {

    @Id
    private String id; // id único del comentario

    private String productoId; // referencia al producto (desde Producto)

    private String usuarioId; // el que hizo la pregunta/comentario

    private String mensaje; // el contenido del comentario o pregunta

    private String respuesta; // la respuesta del dueño de la subasta (puede ser null al inicio)

    private LocalDateTime fechaCreacion;
}
