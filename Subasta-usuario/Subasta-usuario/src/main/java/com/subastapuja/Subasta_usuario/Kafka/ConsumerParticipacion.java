package com.subastapuja.Subasta_usuario.Kafka;

import com.subastapuja.Subasta_usuario.model.DTO.DTOParticipacionSubasta;
import com.subastapuja.Subasta_usuario.service.ParticipacionService;
import org.springframework.kafka.annotation.KafkaListener;

public class ConsumerParticipacion {

    private final ParticipacionService participacionService;

    public ConsumerParticipacion(ParticipacionService participacionService) {
        this.participacionService = participacionService;
    }

    @KafkaListener(
            topics = "participacion-topic",
            groupId = "Participacion-group",
            containerFactory = "ParticipacionSubastaListenerContainerFactory"

    )
    public void consumeParticipacion(DTOParticipacionSubasta dtoParticipacion) {
        participacionService.guardarParticipacionSubasta(dtoParticipacion);
    }
}
