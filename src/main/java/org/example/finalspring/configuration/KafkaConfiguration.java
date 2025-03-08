package org.example.finalspring.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.example.finalspring.event.BookingEvent;
import org.example.finalspring.event.UserEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;


import java.util.HashMap;
import java.util.Map;

@EnableKafka
@Configuration
public class KafkaConfiguration {

    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapServers;

    @Value("${spring.app.groupId}")
    private String groupId;

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        return objectMapper;
    }

    @Bean
    public ProducerFactory<String, UserEvent> userRegEventProducerFactory(ObjectMapper objectMapper) {
        return new DefaultKafkaProducerFactory<>(
                getProducerConfig(),
                new StringSerializer(),
                new JsonSerializer<>(objectMapper));
    }

    @Bean
    public KafkaTemplate<String, UserEvent> userRegEventKafkaTemplate(
            ProducerFactory<String, UserEvent> userRegEventProducerFactory) {
        return new KafkaTemplate<>(userRegEventProducerFactory);
    }


    @Bean
    public ConsumerFactory<String, UserEvent> userRegEventConsumerFactory(ObjectMapper objectMapper) {
        return new DefaultKafkaConsumerFactory<>(getConsumerConfig(), new StringDeserializer(),
                new JsonDeserializer<>(objectMapper));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, UserEvent> userRegEventListenerContainerFactory(
            ConsumerFactory<String, UserEvent> userRegistrationEventConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, UserEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(userRegistrationEventConsumerFactory);
        return factory;
    }

    @Bean
    public ProducerFactory<String, BookingEvent> roomBookingEventProducerFactory(ObjectMapper objectMapper) {
        return new DefaultKafkaProducerFactory<>(
                getProducerConfig(),
                new StringSerializer(),
                new JsonSerializer<>(objectMapper));
    }

    @Bean
    public KafkaTemplate<String, BookingEvent> roomBookingEventKafkaTemplate(
            ProducerFactory<String, BookingEvent> roomBookingEventProducerFactory) {
        return new KafkaTemplate<>(roomBookingEventProducerFactory);
    }

    @Bean
    public ConsumerFactory<String, BookingEvent> roomBookingEventConsumerFactory(ObjectMapper objectMapper) {
        return new DefaultKafkaConsumerFactory<>(getConsumerConfig(), new StringDeserializer(),
                new JsonDeserializer<>(objectMapper));
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, BookingEvent> roomBookingEventListenerContainerFactory(
            ConsumerFactory<String, BookingEvent> roomBookingEventConsumerFactory) {
        ConcurrentKafkaListenerContainerFactory<String, BookingEvent> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(roomBookingEventConsumerFactory);
        return factory;
    }

    private Map<String, Object> getConsumerConfig() {
        Map<String, Object> config = new HashMap<>();
        config.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        config.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        config.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        config.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
        return config;
    }

    private Map<String, Object> getProducerConfig() {
        Map<String, Object> config = new HashMap<>();

        config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return config;
    }


}
