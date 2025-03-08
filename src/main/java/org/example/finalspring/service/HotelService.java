package org.example.finalspring.service;

import lombok.SneakyThrows;
import org.example.finalspring.entity.Hotel;
import org.example.finalspring.entity.Photo;
import org.example.finalspring.model.request.PaginationRequest;
import org.example.finalspring.model.request.hotel.HotelFilterRequest;
import org.example.finalspring.model.request.hotel.MarkRequest;
import org.example.finalspring.model.response.hotel.HotelResponseList;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface HotelService {

    List<Hotel> getAll();

    Hotel findById(Long id);

    Hotel save(Hotel hotel);

    Hotel update(Long id, Hotel hotel);

    void delete(Long id);

    Hotel uploadPhoto(MultipartFile file, Long hotelId);

    Hotel addRating(Long id, MarkRequest request);

    HotelResponseList filterHotels(HotelFilterRequest filterRequest, PaginationRequest pagination);
}
