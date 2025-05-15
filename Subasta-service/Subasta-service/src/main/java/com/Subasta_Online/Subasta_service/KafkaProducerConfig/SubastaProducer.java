package com.Subasta_Online.Subasta_service.KafkaProducerConfig;

import com.Subasta_Online.Subasta_service.Model.DTOiniciarSubasta;
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

}
