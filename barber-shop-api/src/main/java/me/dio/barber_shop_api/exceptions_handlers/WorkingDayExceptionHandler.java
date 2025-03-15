package me.dio.barber_shop_api.exceptions_handlers;

import me.dio.barber_shop_api.exceptions.ErrorResponse;
import me.dio.barber_shop_api.exceptions.WorkingDayAlreadyExists;
import me.dio.barber_shop_api.exceptions.WorkingDayNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class WorkingDayExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(WorkingDayAlreadyExists.class)
    private ResponseEntity<ErrorResponse> handleWorkingDayAlreadyExists(WorkingDayAlreadyExists ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(WorkingDayNotFound.class)
    private ResponseEntity<ErrorResponse> handleWorkingDayNotFound(WorkingDayNotFound ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
