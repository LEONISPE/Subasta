package com.subastapuja.Subasta_usuario.Kafka;
import com.subastapuja.Subasta_usuario.model.DTO.DTOParticipacionSubasta;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, DTOParticipacionSubasta> ParticipacionSubastaConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "Participacion-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class); // OJO: este es de Kafka
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class); // este es de Spring Kafka
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, DTOParticipacionSubasta.class);
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean(name = "ParticipacionSubastaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, DTOParticipacionSubasta> ParticipacionSubastaKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, DTOParticipacionSubasta> factory =
                new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(ParticipacionSubastaConsumerFactory());
        return factory;
    }
}