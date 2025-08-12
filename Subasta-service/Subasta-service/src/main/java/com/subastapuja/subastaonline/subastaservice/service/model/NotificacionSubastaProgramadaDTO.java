package com.subastapuja.subastaonline.subastaservice.service.model;

import lombok.*;

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