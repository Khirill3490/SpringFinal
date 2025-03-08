package org.example.finalspring.util;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.JsonSerializer;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateSerializer extends JsonSerializer<LocalDate> {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    @Override
    public void serialize(LocalDate date, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        String formattedDate = date.format(DATE_FORMATTER);
        jsonGenerator.writeString(formattedDate);
    }
}
