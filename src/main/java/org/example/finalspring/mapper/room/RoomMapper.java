package org.example.finalspring.mapper.room;

import org.example.finalspring.entity.Room;
import org.example.finalspring.model.request.room.RoomRequest;
import org.example.finalspring.model.response.room.RoomResponse;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@DecoratedWith(RoomMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RoomMapper {

    Room toRoom(RoomRequest request);

    RoomResponse toResponse(Room room);
}
