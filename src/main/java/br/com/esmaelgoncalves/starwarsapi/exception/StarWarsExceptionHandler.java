package br.com.esmaelgoncalves.starwarsapi.exception;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class StarWarsExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler({InvalidPlanetException.class})
    public ResponseEntity<Object> handleInvalidPlanetException(InvalidPlanetException ex, WebRequest request) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .status(HttpStatus.BAD_REQUEST)
                .message(ex.getMessage())
                .exception(ex.getClass().getSimpleName()).build();
        return handleExceptionInternal(ex, errorMessage, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler({PlanetNotFoundException.class})
    public ResponseEntity<Object> handlePlanetNotFoundException(PlanetNotFoundException ex, WebRequest request) {
        ErrorMessage errorMessage = ErrorMessage.builder()
                .status(HttpStatus.NOT_FOUND)
                .message(ex.getMessage())
                .exception(ex.getClass().getSimpleName()).build();
        return handleExceptionInternal(ex, errorMessage, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @Data
    @Builder
    public static class ErrorMessage {
        HttpStatus status;
        String message;
        String exception;

    }
}
