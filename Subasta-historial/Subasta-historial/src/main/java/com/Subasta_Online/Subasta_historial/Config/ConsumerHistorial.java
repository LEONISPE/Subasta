package com.Subasta_Online.Subasta_historial.Config;

import com.Subasta_Online.Subasta_historial.Model.DTOPujaActualizada;
import com.Subasta_Online.Subasta_historial.Model.DTOiniciarSubasta;
import com.Subasta_Online.Subasta_historial.Service.ServiceSubastaImpl;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerHistorial {

    private final ServiceSubastaImpl serviceSubasta;

    public ConsumerHistorial(ServiceSubastaImpl serviceSubasta) {
        this.serviceSubasta = serviceSubasta;
    }


    @KafkaListener(topics = "subasta-iniciada-topic",
            groupId = "historial-group",
            containerFactory = "HistorialListenerContainerFactory"
    )
    public void consumirPuja(DTOiniciarSubasta dtOiniciarSubasta) {
        System.out.println("🔥 Listener ACTIVADO para subasta-iniciada-topic");
        System.out.println("🎯 DTO recibido: " + dtOiniciarSubasta);
   serviceSubasta.guardarHistorialDesdeSubastaIniciada(dtOiniciarSubasta);
    }
    @KafkaListener(
            topics = "subasta-actualizada-topic",
            groupId = "Subastas-group-puja",
            containerFactory = "pujaActualizadaListenerFactory"
    )
    public void consumirActualizacionPuja(DTOPujaActualizada dto) {
        serviceSubasta.actualizarHistorialConPuja(dto);
    }

}
