package org.example.finalspring.model.response.room;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.finalspring.entity.Hotel;
import org.example.finalspring.util.LocalDateSerializer;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomResponse {

    private long id;
    private String name;
    private String description;
    private String roomNumber;
    private BigDecimal price;
    private Integer maxOccupancy;

    @JsonSerialize(contentUsing = LocalDateSerializer.class)
    private List<LocalDate> unavailableDates;
    private Long hotelId;
}

