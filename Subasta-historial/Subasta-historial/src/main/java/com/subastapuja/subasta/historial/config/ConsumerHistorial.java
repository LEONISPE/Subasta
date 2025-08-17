package com.subastapuja.subasta.historial.config;

import com.subastapuja.subasta.historial.model.DTOPujaActualizada;
import com.subastapuja.subasta.historial.model.DTOSubastaFinalizadas;
import com.subastapuja.subasta.historial.model.DTOiniciarSubasta;
import com.subastapuja.subasta.historial.service.ServiceSubastaImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerHistorial {

    private final ServiceSubastaImpl serviceSubasta;
    private static final Logger logger = LoggerFactory.getLogger(ConsumerHistorial.class);

    public ConsumerHistorial(ServiceSubastaImpl serviceSubasta) {
        this.serviceSubasta = serviceSubasta;
    }


    @KafkaListener(topics = "subasta-iniciada-topic",
            groupId = "historial-group",
            containerFactory = "HistorialListenerContainerFactory"
    )
    public void consumirPuja(DTOiniciarSubasta dtOiniciarSubasta) {
        logger.info("🔥 Listener ACTIVADO para subasta-iniciada-topic");
        logger.info("dto recibido {}", dtOiniciarSubasta);
   serviceSubasta.guardarHistorialDesdeSubastaIniciada(dtOiniciarSubasta);
    }
    @KafkaListener(
            topics = "subasta-actualizada-topic",
            groupId = "Subastas-group-puja",
            containerFactory = "pujaActualizadaListenerFactory")
    public void consumirActualizacionPuja(DTOPujaActualizada dto) {
        serviceSubasta.actualizarHistorialConPuja(dto);
    }

    @KafkaListener(
            topics = "estado-topic",
            groupId = "Subastas-group-Subasta-Finalizadas",
            containerFactory = "SubastasFinalizadasListenerFactory"
    )
public void consumirFinalisacionEstadoPujas(DTOSubastaFinalizadas dto){
        serviceSubasta.finalizarSubastas(dto);
}

}
