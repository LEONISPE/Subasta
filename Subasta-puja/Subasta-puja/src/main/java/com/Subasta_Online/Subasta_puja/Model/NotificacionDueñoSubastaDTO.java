package com.Subasta_Online.Subasta_puja.Model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class NotificacionDueñoSubastaDTO {
    private String destinatario; // ejemplo: "alberto"
    private String mensaje;
    private String idProducto;
}
