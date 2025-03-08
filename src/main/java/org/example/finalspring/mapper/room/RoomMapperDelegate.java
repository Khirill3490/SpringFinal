package org.example.finalspring.mapper.room;

import org.example.finalspring.entity.Room;
import org.example.finalspring.model.request.room.RoomRequest;
import org.example.finalspring.model.response.room.RoomResponse;
import org.example.finalspring.service.HotelService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.Locale;

public class RoomMapperDelegate implements RoomMapper {

    @Autowired
    private HotelService hotelService;

    @Override
    public Room toRoom(RoomRequest request) {
        return Room.builder()
                .name(request.getName())
                .description(request.getDescription())
                .roomNumber(String.valueOf(request.getRoomNumber()))
                .price(request.getPrice())
                .maxOccupancy(request.getMaxOccupancy())
                .hotel(hotelService.findById(request.getHotelId()))
                .build();
    }

    @Override
    public RoomResponse toResponse(Room room) {
        Collections.sort(room.getUnavailableDates());
        return RoomResponse.builder()
                .id(room.getId())
                .name(room.getName())
                .description(room.getDescription())
                .roomNumber(room.getRoomNumber())
                .price(room.getPrice())
                .maxOccupancy(room.getMaxOccupancy())
                .unavailableDates(room.getUnavailableDates())
                .hotelId(room.getHotel().getId())
                .build();
    }
}
