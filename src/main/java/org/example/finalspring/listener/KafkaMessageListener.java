package org.example.finalspring.listener;

import lombok.RequiredArgsConstructor;
import org.example.finalspring.mapper.booking.BookingMapper;
import org.example.finalspring.mapper.user.UserMapper;
import org.example.finalspring.event.BookingEvent;
import org.example.finalspring.event.UserEvent;
import org.example.finalspring.service.RoomsBookingService;
import org.example.finalspring.service.UsersRegistrationService;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class KafkaMessageListener {

    private final RoomsBookingService roomsBookingService;
    private final UsersRegistrationService usersRegistrationService;
    private final UserMapper userMapper;
    private final BookingMapper bookingMapper;

    @KafkaListener(
            topics = "${spring.app.userRegTopic}",
            groupId = "${spring.app.groupId}",
            containerFactory = "userRegEventListenerContainerFactory")
    public void consumeUserRegistration(@Payload UserEvent event) {
        usersRegistrationService.save(userMapper.toUsersRegistration(event));
    }

    @KafkaListener(
            topics = "${spring.app.roomBookingTopic}",
            groupId = "${spring.app.groupId}",
            containerFactory = "roomBookingEventListenerContainerFactory")
    public void consumeRoomBooking(@Payload BookingEvent event) {
        roomsBookingService.save(bookingMapper.toRoomsBooking(event));
    }
}
