package com.subastapuja.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificacionMejorPostorDTO {
    private String destinatario;
    private String mensaje;
    private String idProducto;
}
