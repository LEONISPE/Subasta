package com.subastapuja.config;

import com.subastapuja.model.DTOFuturasSubastas;
import com.subastapuja.model.DTOiniciarSubasta;
import com.subastapuja.service.PujaServiceImpl;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import org.slf4j.Logger;
@Component
public class ConsumerPuja {

    private final PujaServiceImpl pujaService;
    private static final Logger logger = LoggerFactory.getLogger(ConsumerPuja.class);

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

    @KafkaListener(
            topics = "subasta-futura-iniciada-topic",
            groupId = "Subastas-group",
            containerFactory = "futurasPujasListenerContainerFactory"
    )
    public void consumirPujaFuturas(DTOFuturasSubastas dtoFuturasSubastas) {
        logger.info("subasta futura recibida {}", dtoFuturasSubastas);
        pujaService.guardarSubastasaFuturo(dtoFuturasSubastas);
    }
}
