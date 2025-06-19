package com.Subasta_Online.Subasta_service.KafkaProducerConfig;

import com.Subasta_Online.Subasta_service.Model.DTOFuturasSubastas;
import com.Subasta_Online.Subasta_service.Model.DTOiniciarSubasta;
import com.Subasta_Online.Subasta_service.Model.NotificacionSubastaProgramadaDTO;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SubastaProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public SubastaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessaguePuja(DTOiniciarSubasta dtoiniciarSubasta) {
 kafkaTemplate.send("subasta-iniciada-topic", dtoiniciarSubasta);
    }

    public void sendMensajePujaFuturasPujas(DTOFuturasSubastas dtoFuturasSubastas) {
        kafkaTemplate.send("subasta-futura-iniciada-topic", dtoFuturasSubastas);
    }
    public void sendMensajeNotificacionFuturasSubastas(NotificacionSubastaProgramadaDTO notificacion){
        kafkaTemplate.send("subasta-notificacion-iniciada-topic", notificacion);
    }

}
