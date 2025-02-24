package me.dio.barber_shop_api.exceptions_handlers;

import me.dio.barber_shop_api.exceptions.ServiceBShopNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class ServiceBShopExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ServiceBShopNotFound.class)
    private ResponseEntity<String> serviceNotFound(ServiceBShopNotFound ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
