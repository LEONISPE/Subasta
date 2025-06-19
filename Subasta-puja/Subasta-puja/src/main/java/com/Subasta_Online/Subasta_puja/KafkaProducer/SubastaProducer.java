package com.Subasta_Online.Subasta_puja.KafkaProducer;

import com.Subasta_Online.Subasta_puja.Model.DTOPujaActualizada;
import com.Subasta_Online.Subasta_puja.Model.DTOSubastaFinalizadas;
import com.Subasta_Online.Subasta_puja.Model.NotificacionDueñoSubastaDTO;
import com.Subasta_Online.Subasta_puja.Model.NotificacionMejorPostorDTO;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class SubastaProducer {
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public SubastaProducer(KafkaTemplate<String, Object> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessaguePuja(DTOPujaActualizada dtoPujaActualizada) {
        kafkaTemplate.send("subasta-actualizada-topic", dtoPujaActualizada);
    }
    public void sendNotificacionMejorPostor(NotificacionMejorPostorDTO dto) {
        kafkaTemplate.send("notificacion-topic", dto);
    }

    public void sendNotificacionDueno(NotificacionDueñoSubastaDTO dto) {
        kafkaTemplate.send("notificacion-topic", dto);
    }
    public void sendSubastaFinalizada(DTOSubastaFinalizadas dto) {
        kafkaTemplate.send("estado-topic", dto);
    }
}
