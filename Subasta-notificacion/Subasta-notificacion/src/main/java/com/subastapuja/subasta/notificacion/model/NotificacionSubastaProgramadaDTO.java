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
public class NotificacionSubastaProgramadaDTO {
    private String destinatario;
    private String idProducto;
    private String mensaje;
    private LocalDateTime fechaFuturaInicio;

}