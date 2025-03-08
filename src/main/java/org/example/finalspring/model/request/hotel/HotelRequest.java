package org.example.finalspring.model.request.hotel;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelRequest {

    @NotBlank(message = "Введите название отеля")
    private String name;

    @NotBlank(message = "Введите заголовок объявления отеля")
    private String title;

    @NotBlank(message = "Введите название города")
    private String city;

    @NotBlank(message = "Введите адрес расположения отеля")
    private String address;

    @NotNull(message = "Введите расстояние от центра города до отеля в км")
    private Double distanceFromCenter;
}
