package com.Subasta_Online.Subasta_puja.Config;

import com.Subasta_Online.Subasta_puja.Model.DTOiniciarSubasta;
import com.Subasta_Online.Subasta_puja.Service.PujaServiceImpl;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class ConsumerPuja {

    private final PujaServiceImpl pujaService;

    public ConsumerPuja(PujaServiceImpl pujaService) {
        this.pujaService = pujaService;
    }

    @KafkaListener(
            topics = "subasta-iniciada-topic",
            groupId = "Subastas-group",
            containerFactory = "pujaListenerContainerFactory"
    )
    public void consumirPuja(DTOiniciarSubasta dtOiniciarSubasta) {
   pujaService.guardarPujaDesdeSubastaIniciada(dtOiniciarSubasta);
    }
}
