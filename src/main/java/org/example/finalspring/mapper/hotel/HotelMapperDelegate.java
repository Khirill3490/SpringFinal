package org.example.finalspring.mapper.hotel;

import org.example.finalspring.entity.Hotel;
import org.example.finalspring.mapper.photo.PhotoMapper;
import org.example.finalspring.model.request.hotel.HotelRequest;
import org.example.finalspring.model.response.hotel.HotelResponse;
import org.example.finalspring.model.response.photo.PhotoResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class HotelMapperDelegate implements HotelMapper {

    @Autowired
    private PhotoMapper photoMapper;

    @Override
    public Hotel toHotel(HotelRequest hotelRequest) {
        return Hotel.builder()
                .name(hotelRequest.getName())
                .title(hotelRequest.getTitle())
                .city(hotelRequest.getCity())
                .address(hotelRequest.getAddress())
                .distanceFromCenter(hotelRequest.getDistanceFromCenter())
                .build();
    }

    @Override
    public HotelResponse toResponse(Hotel hotel) {
        var roundedRating = Math.round(hotel.getRating() * 10.0) / 10.0;

        List<PhotoResponse> photoResponseList = hotel.getPhotos()
                .stream()
                .map(photoMapper::toResponse)
                .toList();

        return HotelResponse.builder()
                .id(hotel.getId())
                .name(hotel.getName())
                .title(hotel.getTitle())
                .city(hotel.getCity())
                .address(hotel.getAddress())
                .distanceFromCenter(hotel.getDistanceFromCenter())
                .rating(roundedRating)
                .countOfRatings(hotel.getNumberOfRating())
                .photos(photoResponseList)
                .build();
    }
}
