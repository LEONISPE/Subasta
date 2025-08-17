package com.subastapuja.kafkaproducer;

import com.subastapuja.model.*;
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

    public void sendNotificacionDueno(NotificacionDuenoSubastaDTO dto) {
        kafkaTemplate.send("notificacion-topic", dto);
    }
    public void sendSubastaFinalizada(DTOSubastaFinalizadas dto) {
        kafkaTemplate.send("estado-topic", dto);
    }

    public void sendParticipacionSubasta(DTOParticipacionSubasta dtoParticipacionSubasta){
        System.out.println("enviando participacion de subasta" +dtoParticipacionSubasta);
        kafkaTemplate.send("participacion-topic", dtoParticipacionSubasta);
    }
    public void sendComentarios(DTOComentarios dtoComentarios){
        kafkaTemplate.send("Comentarios", dtoComentarios);
    }

}
