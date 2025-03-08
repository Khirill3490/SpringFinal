package org.example.finalspring.model.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.finalspring.exception.IncorrectDataException;
import org.example.finalspring.util.LocalDateDeserializer;

import java.time.LocalDate;

@Data
@Builder
public class BookingRequest {

    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate checkInDate;

    @JsonDeserialize(using = LocalDateDeserializer.class)
    private LocalDate checkOutDate;


    @Min(1)
    @NotNull(message = "Введите id номера")
    private Long roomId;

    public BookingRequest() {
        validate();
    }

    @Builder
    public BookingRequest(LocalDate checkInDate, LocalDate checkOutDate, Long roomId) {
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.roomId = roomId;
        validate();
    }

    public void validate() {
        if (checkInDate != null && checkOutDate != null) {
            if (checkInDate.isEqual(checkOutDate)) {
                throw new IncorrectDataException("Заселение и отъезд не могут быть в один день");
            }
            if (checkInDate.isAfter(checkOutDate)) {
                throw new IncorrectDataException("Дата отъезда не может быть раньше даты заселения.");
            }
        }
    }
}
