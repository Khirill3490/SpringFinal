package org.example.finalspring.model.response.hotel;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.finalspring.entity.Photo;
import org.example.finalspring.model.response.photo.PhotoResponse;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelResponse {
    private Long id;
    private String name;
    private String title;
    private String city;
    private String address;
    private Double distanceFromCenter;
    private Double rating;
    private Integer countOfRatings;
    private List<PhotoResponse> photos;
}
