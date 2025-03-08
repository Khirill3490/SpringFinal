package org.example.finalspring.service;

import org.example.finalspring.entity.Booking;
import org.example.finalspring.model.request.PaginationRequest;

import java.util.List;

public interface BookingService {

    List<Booking> getAll(PaginationRequest pagination);

    Booking bookRoom(Booking booking);
}
