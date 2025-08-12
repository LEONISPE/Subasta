package com.subastapuja.subastaonline.subastaservice.service.kafkaproducerconfig;

import com.subastapuja.subastaonline.subastaservice.service.model.DTOFuturasSubastas;
import com.subastapuja.subastaonline.subastaservice.service.model.DTOiniciarSubasta;
import com.subastapuja.subastaonline.subastaservice.service.model.NotificacionSubastaProgramadaDTO;
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
