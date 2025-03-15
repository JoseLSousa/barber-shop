package me.dio.barber_shop_api.exceptions_handlers;

import me.dio.barber_shop_api.exceptions.BookingAlreadyExists;
import me.dio.barber_shop_api.exceptions.BookingNotFound;
import me.dio.barber_shop_api.exceptions.BookingOutOfWorkingTimeBounds;
import me.dio.barber_shop_api.exceptions.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class BookingExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BookingNotFound.class)
    private ResponseEntity<ErrorResponse> handleBookingNotFound(BookingNotFound ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookingAlreadyExists.class)
    private ResponseEntity<ErrorResponse> handleBookingAlreadyExists(BookingAlreadyExists ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookingOutOfWorkingTimeBounds.class)
    private ResponseEntity<ErrorResponse> handleBookingOutOfWorkingTimeBounds(BookingOutOfWorkingTimeBounds ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
