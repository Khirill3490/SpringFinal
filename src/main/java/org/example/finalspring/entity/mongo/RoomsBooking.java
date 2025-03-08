package org.example.finalspring.entity.mongo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "rooms_booking")
public class RoomsBooking {

    @Id
    private String id;
    private Long userId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private LocalDateTime bookingDate;
}
