package org.example.finalspring.mapper.booking;

import org.example.finalspring.entity.Booking;
import org.example.finalspring.model.request.BookingRequest;
import org.example.finalspring.model.response.booking.BookingResponse;
import org.example.finalspring.service.RoomService;
import org.example.finalspring.service.UserService;
import org.example.finalspring.event.BookingEvent;
import org.example.finalspring.entity.mongo.RoomsBooking;
import org.springframework.beans.factory.annotation.Autowired;

public class BookingMapperDelegate implements BookingMapper {

    @Autowired
    private UserService userService;

    @Autowired
    private RoomService roomService;

    @Override
    public Booking toBooking(BookingRequest request, Long userId) {
        return Booking.builder()
                .checkInDate(request.getCheckInDate())
                .checkOutDate(request.getCheckOutDate())
                .room(roomService.findById(request.getRoomId()))
                .user(userService.findById(userId))
                .build();
    }

    @Override
    public BookingResponse toBookingResponse(Booking booking) {
        return BookingResponse.builder()
                .checkInDate(booking.getCheckInDate())
                .checkOutDate(booking.getCheckOutDate())
                .roomId(booking.getRoom().getId())
                .userId(booking.getUser().getId())
                .build();
    }

    @Override
    public BookingEvent toBookingEvent(Booking booking) {
        return BookingEvent.builder()
                .userId(booking.getUser().getId())
                .checkInDate(booking.getCheckInDate())
                .checkOutDate(booking.getCheckOutDate())
                .build();
    }

    @Override
    public RoomsBooking toRoomsBooking(BookingEvent event) {
        return RoomsBooking.builder()
                .userId(event.getUserId())
                .checkInDate(event.getCheckInDate())
                .checkOutDate(event.getCheckOutDate())
                .build();
    }
}
