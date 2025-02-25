package me.dio.barber_shop_api.exceptions_handlers;

import me.dio.barber_shop_api.exceptions.WorkingDayAlreadyExists;
import me.dio.barber_shop_api.exceptions.WorkingDayNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class WorkingDayExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(WorkingDayAlreadyExists.class)
    private ResponseEntity<String> workingDayAlreadyExists(WorkingDayAlreadyExists exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(exception.getMessage());
    }

    @ExceptionHandler(WorkingDayNotFound.class)
    private ResponseEntity<String> workingDayNotFound(WorkingDayNotFound exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(exception.getMessage());
    }

}
