package org.example.finalspring.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.finalspring.entity.Hotel;
import org.example.finalspring.entity.Photo;
import org.example.finalspring.mapper.hotel.HotelMapper;
import org.example.finalspring.model.request.hotel.HotelRequest;
import org.example.finalspring.model.request.PaginationRequest;
import org.example.finalspring.model.request.hotel.HotelFilterRequest;
import org.example.finalspring.model.request.hotel.MarkRequest;
import org.example.finalspring.model.response.hotel.HotelResponse;
import org.example.finalspring.model.response.hotel.HotelResponseList;
import org.example.finalspring.service.HotelService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/v1/hotel")
@RequiredArgsConstructor
public class HotelController {

    private final HotelService hotelService;
    private final HotelMapper hotelMapper;

    @GetMapping
    public ResponseEntity<HotelResponseList> getAll() {
        List<HotelResponse> hotels = hotelService.getAll()
                .stream()
                .map(hotelMapper::toResponse)
                .toList();

        HotelResponseList responseList = new HotelResponseList();
        responseList.setHotels(hotels);
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HotelResponse> getById(@PathVariable Long id) {
        return ResponseEntity.ok(hotelMapper.toResponse(hotelService.findById(id)));
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HotelResponse> create(@RequestBody @Valid HotelRequest request) {
        Hotel hotel = hotelService.save(hotelMapper.toHotel(request));
        return ResponseEntity.ok(hotelMapper.toResponse(hotel));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HotelResponse> update(@PathVariable Long id, @Valid @RequestBody HotelRequest request) {
        Hotel hotel = hotelService.update(id, hotelMapper.toHotel(request));
        return ResponseEntity.ok(hotelMapper.toResponse(hotel));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        hotelService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{hotelId}/photo/upload")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HotelResponse> uploadPhoto(@PathVariable Long hotelId, @RequestParam("file") MultipartFile file) {
        Hotel hotel = hotelService.uploadPhoto(file, hotelId);
        return ResponseEntity.ok(hotelMapper.toResponse(hotel));

    }

    @PostMapping("/{id}/rating")
    public ResponseEntity<HotelResponse> changeRating(@PathVariable Long id, @Valid @ModelAttribute MarkRequest request) {
        return ResponseEntity.ok(hotelMapper.toResponse(hotelService.addRating(id, request)));
    }

    @GetMapping("/filter")
    public ResponseEntity<HotelResponseList> filterHotels(@ModelAttribute HotelFilterRequest request,
                                                          @ModelAttribute PaginationRequest pagination) {
        HotelResponseList response = hotelService.filterHotels(request, pagination);
        return ResponseEntity.ok(response);
    }



}
