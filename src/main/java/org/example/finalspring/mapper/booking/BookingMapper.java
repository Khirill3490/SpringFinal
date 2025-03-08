package org.example.finalspring.mapper.booking;


import org.example.finalspring.entity.Booking;
import org.example.finalspring.model.request.BookingRequest;
import org.example.finalspring.model.response.booking.BookingResponse;
import org.example.finalspring.event.BookingEvent;
import org.example.finalspring.entity.mongo.RoomsBooking;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@DecoratedWith(BookingMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BookingMapper {

    Booking toBooking(BookingRequest request, Long userId);

    BookingResponse toBookingResponse(Booking booking);

    BookingEvent toBookingEvent(Booking booking);

    RoomsBooking toRoomsBooking(BookingEvent event);
}
