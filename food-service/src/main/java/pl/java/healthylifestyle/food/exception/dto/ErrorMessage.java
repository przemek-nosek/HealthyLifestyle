package pl.java.healthylifestyle.food.exception.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Collection;

public record ErrorMessage(
        HttpStatus httpStatus,
        int statusCode,
        LocalDateTime timeStamp,
        String message,
        @JsonInclude(value = JsonInclude.Include.NON_NULL)
        String label,
        @JsonInclude(value = JsonInclude.Include.NON_EMPTY)
        Collection<String> errors) {
}
