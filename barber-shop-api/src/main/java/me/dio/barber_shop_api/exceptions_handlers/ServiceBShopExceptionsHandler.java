package me.dio.barber_shop_api.exceptions_handlers;

import me.dio.barber_shop_api.exceptions.ErrorResponse;
import me.dio.barber_shop_api.exceptions.ServiceBShopNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class ServiceBShopExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ServiceBShopNotFound.class)
    private ResponseEntity<ErrorResponse> handleServiceNotFound(ServiceBShopNotFound ex, WebRequest request) {
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
