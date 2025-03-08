package org.example.finalspring.model.response.booking;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.finalspring.util.LocalDateSerializer;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingResponse {

    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate checkInDate;

    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate checkOutDate;
    private Long roomId;
    private Long userId;
}
