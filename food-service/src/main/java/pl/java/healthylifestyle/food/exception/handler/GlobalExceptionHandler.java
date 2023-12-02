package pl.java.healthylifestyle.food.exception.handler;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import pl.java.healthylifestyle.food.exception.FoodAlreadyExistsException;
import pl.java.healthylifestyle.food.exception.dto.ErrorMessage;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    private static final LocalDateTime NOW = LocalDateTime.now();

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ErrorMessage> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        return new ResponseEntity<>(new ErrorMessage(HttpStatus.FORBIDDEN, HttpStatus.FORBIDDEN.value(), NOW,
                ex.getMessage(), null, new ArrayList<>()), new HttpHeaders(), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(FoodAlreadyExistsException.class)
    protected ResponseEntity<ErrorMessage> handleMeasurementAlreadyExistsException(FoodAlreadyExistsException ex) {
        ErrorMessage errorMessage = getErrorMessage(HttpStatus.BAD_REQUEST, ex.getMessage(), ex.getErrorLabel(), Collections.emptyList());
        return new ResponseEntity<>(errorMessage, HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            @NonNull HttpHeaders headers,
            @NonNull HttpStatusCode status,
            @NonNull WebRequest request) {
        List<String> errors = new ArrayList<>();
        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.add(fieldName + " - " + errorMessage);
        });

        return new ResponseEntity<>(getErrorMessage((HttpStatus) status, ex.getMessage(), null, errors), status);
    }


    private ErrorMessage getErrorMessage(HttpStatus httpStatus, String message, String errorLabel, Collection<String> errors) {
        return new ErrorMessage(httpStatus, httpStatus.value(), LocalDateTime.now(), message, errorLabel, errors);
    }
}
