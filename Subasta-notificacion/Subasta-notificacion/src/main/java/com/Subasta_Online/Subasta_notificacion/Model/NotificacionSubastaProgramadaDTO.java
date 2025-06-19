package com.Subasta_Online.Subasta_notificacion.Model;

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