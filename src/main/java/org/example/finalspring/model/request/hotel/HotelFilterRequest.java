package org.example.finalspring.model.request.hotel;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HotelFilterRequest {

    @Positive(message = "id должен иметь положительное значение")
    private Long id;

    @Size(max = 20, message = "Имя не должно превышать 20 символов")
    private String name;

    @Size(max = 20, message = "Заголовок не должен превышать 20 символов")
    private String title;

    @Size(max = 20, message = "Название города не должно превышать 20 символов")
    private String city;

    @Size(max = 40, message = "Андрес не должен превышать 40 символов")
    private String address;

    @Positive(message = "Расстояние до центра должно быть положитльным значением")
    @Max(value = 30, message = "Расстояние от центра города не может превышать 30 км")
    private Double distanceFromCenter;

    @Min(value = 1, message = "Минимальное значение должно быть не менее 1")
    @Max(value = 5, message = "Максимальное значение должно быть не более 5")
    private Double rating;

    @Positive(message = "Поле numberOfRating должно быть положительным")
    private Integer numberOfRating;

}
