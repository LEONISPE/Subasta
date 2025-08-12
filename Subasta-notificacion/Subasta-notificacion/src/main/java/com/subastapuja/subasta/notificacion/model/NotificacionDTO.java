package com.subastapuja.subasta.notificacion.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificacionDTO {
    private String destinatario;
    private String mensaje;
    private String idProducto;
    private LocalDateTime fecha;
}
