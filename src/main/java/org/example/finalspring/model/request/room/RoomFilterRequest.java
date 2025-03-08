package org.example.finalspring.model.request.room;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.finalspring.util.LocalDateDeserializer;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomFilterRequest {

    @Positive(message = "id должен иметь положительное значение")
    private Long id;

    @Size(max = 20, message = "Имя не должно превышать 20 символов")
    private String name;

    @Positive(message = "Минимальная цена должна быть положительной")
    private Double minPrice;

    @Positive(message = "Максимальная цена должна быть положительной")
    private Double maxPrice;

    @Positive(message = "Максимальная вместимость должна быть положительной")
    private Integer maxOccupancy;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate checkInDate;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate checkOutDate;

    @Positive(message = "hotelId должен иметь положительное значение")
    private Long hotelId;
}
