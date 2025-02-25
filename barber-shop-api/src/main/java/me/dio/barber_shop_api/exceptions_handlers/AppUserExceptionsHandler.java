package me.dio.barber_shop_api.exceptions_handlers;

import me.dio.barber_shop_api.exceptions.EmailAlreadyRegistered;
import me.dio.barber_shop_api.exceptions.PasswordException;
import me.dio.barber_shop_api.exceptions.TokenExpiredOrUserNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class AppUserExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PasswordException.class)
    private ResponseEntity<String> passwordException(PasswordException ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(EmailAlreadyRegistered.class)
    private ResponseEntity<String> emailAlreadyRegistered(EmailAlreadyRegistered ex) {
        return ResponseEntity.badRequest().body(ex.getMessage());
    }

    @ExceptionHandler(TokenExpiredOrUserNotFound.class)
    private ResponseEntity<String> tokenExpiredOrUserNotFound(TokenExpiredOrUserNotFound ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getMessage());
    }
}
