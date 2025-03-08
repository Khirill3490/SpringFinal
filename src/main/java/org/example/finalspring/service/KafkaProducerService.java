package org.example.finalspring.service;

import org.example.finalspring.event.BookingEvent;
import org.example.finalspring.event.UserEvent;

public interface KafkaProducerService {

    void sendRoomBookingEvent(BookingEvent bookingEvent);

    void sendUserRegistrationEvent(UserEvent userEvent);
}
