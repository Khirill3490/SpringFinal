package org.example.finalspring.model.response.hotel;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotelResponseList {

    private List<HotelResponse> hotels;
    private long totalCount;
}
