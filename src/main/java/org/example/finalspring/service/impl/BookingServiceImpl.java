package org.example.finalspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.finalspring.entity.Booking;
import org.example.finalspring.entity.Room;
import org.example.finalspring.exception.IncorrectDataException;
import org.example.finalspring.model.request.PaginationRequest;
import org.example.finalspring.repository.BookingRepository;
import org.example.finalspring.service.BookingService;
import org.example.finalspring.service.RoomService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingServiceImpl implements BookingService {

    private final BookingRepository bookingRepository;
    private final RoomService roomService;

    @Override
    public List<Booking> getAll(PaginationRequest pagination) {
        PageRequest pageable = PageRequest.of(pagination.getPageNumber(), pagination.getPageSize());
        return bookingRepository.findAll(pageable).getContent();
    }

    @Override
    public Booking bookRoom(Booking booking) {
        boolean existsByRoomAndDateRange = bookingRepository.existsByRoomAndDateRange(
                booking.getRoom().getId(),
                booking.getCheckInDate(),
                booking.getCheckOutDate());

        if (existsByRoomAndDateRange) {
            throw new IncorrectDataException("Комната недоступна на желаемые даты");
        }

        Room room = booking.getRoom();

        List<LocalDate> unavailableDates = addUnavailableDatesForRoom(
                room.getUnavailableDates(),
                booking.getCheckInDate(),
                booking.getCheckOutDate());

        room.setUnavailableDates(unavailableDates);

        roomService.save(room);

        booking.setRoom(room);

        return bookingRepository.save(booking);
    }

    private List<LocalDate> addUnavailableDatesForRoom(List<LocalDate>  dateList, LocalDate checkInDate, LocalDate checkOutDate) {
        List<LocalDate> datesToAdd = checkInDate.datesUntil(checkOutDate.plusDays(1)).toList();

        dateList.addAll(datesToAdd);
        dateList = dateList.stream().distinct().collect(Collectors.toList());

        return dateList;
    }

    private boolean isDateRangeUnavailable(Room room, LocalDate checkInDate, LocalDate checkOutDate) {
        List<LocalDate> unavailableDates = room.getUnavailableDates();

        return unavailableDates.stream().anyMatch(date ->
                !date.isBefore(checkInDate) && !date.isAfter(checkOutDate));
    }
}
