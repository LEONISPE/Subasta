package com.subastapuja.subasta.producto.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DTOComentarios {

    private String productoId;

    private String usuarioId;

    private String mensaje;

    private String respuesta;

    private LocalDateTime fechaCreacion;
}
