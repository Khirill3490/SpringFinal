package org.example.finalspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.finalspring.entity.Room;
import org.example.finalspring.mapper.room.RoomMapper;
import org.example.finalspring.model.request.PaginationRequest;
import org.example.finalspring.model.request.room.RoomRequest;
import org.example.finalspring.model.request.room.RoomFilterRequest;
import org.example.finalspring.model.response.room.RoomResponse;
import org.example.finalspring.model.response.room.RoomResponseList;
import org.example.finalspring.service.RoomService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/room")
@RequiredArgsConstructor
public class RoomController {

    private final RoomService roomService;
    private final RoomMapper roomMapper;

    @GetMapping("/{id}")
    public ResponseEntity<RoomResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(roomMapper.toResponse(roomService.findById(id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoomResponse> create(@Valid @RequestBody RoomRequest request) {
        Room room = roomService.save(roomMapper.toRoom(request));
        return ResponseEntity.ok(roomMapper.toResponse(room));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<RoomResponse> update(@PathVariable Long id, @Valid @RequestBody RoomRequest request) {
        Room room = roomService.update(id, roomMapper.toRoom(request));
        return ResponseEntity.ok(roomMapper.toResponse(room));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roomService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/filter")
    public ResponseEntity<RoomResponseList> filterRooms(@ModelAttribute RoomFilterRequest request,
                                                         @ModelAttribute PaginationRequest pagination) {
        RoomResponseList response = roomService.filterRooms(request, pagination);
        return ResponseEntity.ok(response);
    }
}
