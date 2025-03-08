package org.example.finalspring.mapper.hotel;

import org.example.finalspring.entity.Hotel;
import org.example.finalspring.model.request.hotel.HotelRequest;
import org.example.finalspring.model.response.hotel.HotelResponse;
import org.mapstruct.DecoratedWith;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@DecoratedWith(HotelMapperDelegate.class)
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface HotelMapper {

    Hotel toHotel(HotelRequest hotelRequest);

//    UpsertHotelRequest toUpsertHotelRequest(Hotel hotel);

    HotelResponse toResponse(Hotel hotel);
}
