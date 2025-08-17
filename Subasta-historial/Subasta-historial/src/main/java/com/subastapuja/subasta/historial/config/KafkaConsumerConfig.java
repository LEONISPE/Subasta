package com.subastapuja.subasta.historial.config;
import com.subastapuja.subasta.historial.model.DTOPujaActualizada;
import com.subastapuja.subasta.historial.model.DTOSubastaFinalizadas;
import com.subastapuja.subasta.historial.model.DTOiniciarSubasta;
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

    private static final String BOOTSTRAP_SERVERS = "localhost:9092";
    @Bean
    public ConsumerFactory<String, DTOiniciarSubasta> HistorialConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "historial-group");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.subastapuja.subasta.historial.model.DTOiniciarSubasta"); // <-- Asegúrate de cambiar esto con el path real de la clase
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean(name = "HistorialListenerContainerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, DTOiniciarSubasta> historialKafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, DTOiniciarSubasta> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(HistorialConsumerFactory());
        return factory;
    }
    @Bean
    public ConsumerFactory<String, DTOPujaActualizada> pujaActualizadaConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "Subastas-group-puja");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.subastapuja.subasta.historial.model.DTOPujaActualizada"); // ⚠️ Asegúrate de que el path esté correcto
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean(name = "pujaActualizadaListenerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, DTOPujaActualizada> pujaActualizadaListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, DTOPujaActualizada> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(pujaActualizadaConsumerFactory());
        return factory;
    }

    @Bean
    public ConsumerFactory<String, DTOSubastaFinalizadas> SubastasFinalizadasConsumerFactory() {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "Subastas-group-Subasta-Finalizadas");
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(JsonDeserializer.VALUE_DEFAULT_TYPE, "com.subastapuja.subasta.historial.model.DTOSubastaFinalizadas"); // ⚠️ Asegúrate de que el path esté correcto
        props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        props.put(JsonDeserializer.USE_TYPE_INFO_HEADERS, false);

        return new DefaultKafkaConsumerFactory<>(props);
    }

    @Bean(name = "SubastasFinalizadasListenerFactory")
    public ConcurrentKafkaListenerContainerFactory<String, DTOSubastaFinalizadas>SubastasFinalizadasListenerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, DTOSubastaFinalizadas> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(SubastasFinalizadasConsumerFactory());
        return factory;
    }


}
