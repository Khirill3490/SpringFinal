package org.example.finalspring.service;

import org.example.finalspring.entity.Room;
import org.example.finalspring.model.request.PaginationRequest;
import org.example.finalspring.model.request.room.RoomFilterRequest;
import org.example.finalspring.model.response.room.RoomResponseList;

import java.util.List;

public interface RoomService {

    Room findById(Long id);

    Room save(Room Room);

    Room update(Long id, Room Room);

    void delete(Long id);

    RoomResponseList filterRooms(RoomFilterRequest filterRequest, PaginationRequest pagination);
}
