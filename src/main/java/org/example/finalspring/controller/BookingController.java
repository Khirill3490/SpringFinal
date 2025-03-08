package org.example.finalspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.finalspring.entity.Booking;
import org.example.finalspring.mapper.booking.BookingMapper;
import org.example.finalspring.model.request.BookingRequest;
import org.example.finalspring.model.request.PaginationRequest;
import org.example.finalspring.model.response.booking.BookingResponse;
import org.example.finalspring.model.response.booking.BookingResponseList;
import org.example.finalspring.security.AppUserPrincipal;
import org.example.finalspring.service.BookingService;
import org.example.finalspring.service.KafkaProducerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/booking")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final BookingMapper bookingMapper;
    private final KafkaProducerService kafkaProducerService;

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<BookingResponseList> getAll(PaginationRequest pagination) {
        List<BookingResponse> hotels = bookingService.getAll(pagination)
                .stream()
                .map(bookingMapper::toBookingResponse)
                .toList();

        BookingResponseList responseList = new BookingResponseList(hotels);
        return ResponseEntity.ok(responseList);
    }

    @PostMapping
    public ResponseEntity<BookingResponse> book(@Valid @RequestBody BookingRequest request,
                                                @AuthenticationPrincipal AppUserPrincipal principal) {
        Booking booking = bookingService.bookRoom(bookingMapper.toBooking(request, principal.getId()));

        kafkaProducerService.sendRoomBookingEvent(bookingMapper.toBookingEvent(booking));

        return ResponseEntity.ok(bookingMapper.toBookingResponse(booking));
    }
}
