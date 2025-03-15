package me.dio.barber_shop_api.exceptions_handlers;

import me.dio.barber_shop_api.exceptions.EmailAlreadyRegistered;
import me.dio.barber_shop_api.exceptions.ErrorResponse;
import me.dio.barber_shop_api.exceptions.PasswordException;
import me.dio.barber_shop_api.exceptions.TokenExpiredOrUserNotFound;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class AppUserExceptionsHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(PasswordException.class)
    private ResponseEntity<ErrorResponse> handlePasswordException(PasswordException ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EmailAlreadyRegistered.class)
    private ResponseEntity<ErrorResponse> handleEmailAlreadyRegistered(EmailAlreadyRegistered ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TokenExpiredOrUserNotFound.class)
    private ResponseEntity<ErrorResponse> handleTokenExpiredOrUserNotFound(TokenExpiredOrUserNotFound ex, WebRequest request) {
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                HttpStatus.UNAUTHORIZED.getReasonPhrase(),
                ex.getMessage(),
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
}
