package org.example.finalspring.service;

import org.example.finalspring.entity.mongo.RoomsBooking;

import java.util.List;

public interface RoomsBookingService {

    RoomsBooking save(RoomsBooking roomsBooking);

    List<RoomsBooking> findAll();
}
