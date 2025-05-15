package com.Subasta_Online.Subasta_puja.Config;

import com.Subasta_Online.Subasta_puja.Model.DTOiniciarSubasta;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class KafkaConsumerConfig {

    @Bean
    public ConsumerFactory<String, DTOiniciarSubasta> pujaConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "Subastas-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.Subasta_Online.Subasta_puja.Model.DTOiniciarSubasta"); // <-- Asegúrate de cambiar esto con el path real de la clase
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean(name = "pujaListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, DTOiniciarSubasta> pujaKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, DTOiniciarSubasta> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(pujaConsumerFactory());
        return factory;
    }
}
