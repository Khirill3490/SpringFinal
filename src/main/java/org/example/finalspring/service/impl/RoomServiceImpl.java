package org.example.finalspring.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.finalspring.entity.Room;
import org.example.finalspring.exception.EntityNotFoundException;
import org.example.finalspring.mapper.room.RoomMapper;
import org.example.finalspring.model.request.PaginationRequest;
import org.example.finalspring.model.request.room.RoomFilterRequest;
import org.example.finalspring.model.response.room.RoomResponseList;
import org.example.finalspring.repository.RoomRepository;
import org.example.finalspring.service.RoomService;
import org.example.finalspring.specification.RoomSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    private final RoomSpecification roomSpecification;
    private final RoomMapper roomMapper;

    @Override
    public Room findById(Long id) {
        return roomRepository.findById(id).orElseThrow(() ->
                new EntityNotFoundException("Комната с id " + id + " не найдена"));
    }

    @Override
    public Room save(Room room) {
        if (room.getUnavailableDates() == null) {
            room.setUnavailableDates(List.of());
        }
        return roomRepository.save(room);
    }

    @Override
    public Room update(Long id, Room room) {
        Room roomToUpdate = findById(id);

        roomToUpdate.setName(room.getName());
        roomToUpdate.setDescription(room.getDescription());
        roomToUpdate.setRoomNumber(room.getRoomNumber());
        roomToUpdate.setPrice(room.getPrice());
        roomToUpdate.setMaxOccupancy(room.getMaxOccupancy());
        roomToUpdate.setHotel(room.getHotel());

        if (room.getUnavailableDates() != null) {
            roomToUpdate.setUnavailableDates(room.getUnavailableDates());
        }

        return roomRepository.save(roomToUpdate);
    }

    @Override
    public void delete(Long id) {
        Room room = findById(id);
        roomRepository.delete(room);
    }

    @Override
    public RoomResponseList filterRooms(RoomFilterRequest filterRequest, PaginationRequest pagination) {
        Specification<Room> specification = Specification.where(roomSpecification.filterById(filterRequest.getId()))
                .and(roomSpecification.filterByName(filterRequest.getName()))
                .and(roomSpecification.filterByPriceRange(filterRequest.getMinPrice(), filterRequest.getMaxPrice()))
                .and(roomSpecification.filterByGuestCapacity(filterRequest.getMaxOccupancy()))
                .and(roomSpecification.filterByCheckInOutDates(filterRequest.getCheckInDate(), filterRequest.getCheckOutDate()))
                .and(roomSpecification.filterByHotelId(filterRequest.getHotelId()));

        PageRequest pageRequest = PageRequest.of(pagination.getPageNumber(), pagination.getPageSize());
        Page<Room> roomsPage = roomRepository.findAll(specification, pageRequest);

        return new RoomResponseList(roomsPage.getContent()
                .stream()
                .map(roomMapper::toResponse)
                .toList(), roomsPage.getTotalElements());
    }
}
