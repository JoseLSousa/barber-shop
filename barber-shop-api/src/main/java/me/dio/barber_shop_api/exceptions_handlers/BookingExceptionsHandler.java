package me.dio.barber_shop_api.exceptions_handlers;

import me.dio.barber_shop_api.exceptions.BookingAlreadyExists;
import me.dio.barber_shop_api.exceptions.BookingNotFound;
import me.dio.barber_shop_api.exceptions.BookingOutOfWorkingTimeBounds;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class BookingExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BookingNotFound.class)
    private ResponseEntity<String> bookingNotFound(BookingNotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(BookingAlreadyExists.class)
    private ResponseEntity<String> bookingAlreadyExists(BookingAlreadyExists ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(BookingOutOfWorkingTimeBounds.class)
    private ResponseEntity<String> bookingOutOfWorkingTimeBounds(BookingOutOfWorkingTimeBounds ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }
}
