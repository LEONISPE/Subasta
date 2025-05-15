package com.Subasta_Online.Subasta_puja.Config;

import com.Subasta_Online.Subasta_puja.Model.DTOiniciarSubasta;
import com.Subasta_Online.Subasta_puja.Service.PujaService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerPuja {

    private final PujaService pujaService;

    public ConsumerPuja(PujaService pujaService) {
        this.pujaService = pujaService;
    }

    @KafkaListener(
            topics = "subasta-iniciada-topic",
            groupId = "Subastas-group",
            containerFactory = "pujaListenerContainerFactory"
    )
    public void consumirPuja(DTOiniciarSubasta dtOiniciarSubasta) {
   pujaService.guardarPuja(dtOiniciarSubasta);
    }
}
