package org.example.finalspring.model.request.room;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoomRequest {

    @NotBlank(message = "Введите название комнаты")
    private String name;

    @NotBlank(message = "Введите описание комнаты")
    private String description;

    @NotBlank(message = "Введите номер комнаты")
    private String roomNumber;

    @NotNull(message = "Введите цену за номер")
    private BigDecimal price;

    @NotNull(message = "Введите максимальную вместимость комнаты")
    private Integer maxOccupancy;

    @Min(1)
    @NotNull(message = "Поле hotelId не должно быть пустым")
    private Long hotelId;
}
