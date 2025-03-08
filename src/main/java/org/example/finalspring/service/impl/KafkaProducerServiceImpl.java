package org.example.finalspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.finalspring.event.BookingEvent;
import org.example.finalspring.event.UserEvent;
import org.example.finalspring.service.KafkaProducerService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaProducerServiceImpl implements KafkaProducerService {

    @Value("${spring.app.userRegTopic}")
    private String userRegTopic;

    @Value("${spring.app.roomBookingTopic}")
    private String roomBookingTopic;

    private final KafkaTemplate<String, UserEvent> userRegEventKafkaTemplate;
    private final KafkaTemplate<String, BookingEvent> roomBookingEventKafkaTemplate;

    @Override
    public void sendUserRegistrationEvent(UserEvent userEvent) {
        userRegEventKafkaTemplate.send(userRegTopic, userEvent);
    }

    @Override
    public void sendRoomBookingEvent(BookingEvent bookingEvent) {
        roomBookingEventKafkaTemplate.send(roomBookingTopic, bookingEvent);
    }
}
