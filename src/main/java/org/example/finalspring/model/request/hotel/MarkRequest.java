package org.example.finalspring.model.request.hotel;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
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
public class MarkRequest {


    @Min(value = 1, message = "Минимальное значение должно быть 1")
    @Max(value = 5, message = "Максимальное значение должно быть 5")
    @NotNull(message = "Введите оценку отеля")
    private Integer newMark;
}
