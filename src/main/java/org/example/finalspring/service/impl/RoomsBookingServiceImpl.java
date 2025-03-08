package org.example.finalspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.finalspring.entity.mongo.RoomsBooking;
import org.example.finalspring.repository.mongoRep.RoomsBookingRepository;
import org.example.finalspring.service.RoomsBookingService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoomsBookingServiceImpl implements RoomsBookingService {

    private final RoomsBookingRepository repository;

    @Override
    public RoomsBooking save(RoomsBooking roomsBooking) {
        roomsBooking.setId(UUID.randomUUID().toString());
        roomsBooking.setBookingDate(LocalDateTime.now());
        return repository.save(roomsBooking);
    }

    @Override
    public List<RoomsBooking> findAll() {
        return repository.findAll();
    }
}
