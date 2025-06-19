package com.Subasta_Online.Subasta_service.Model;

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