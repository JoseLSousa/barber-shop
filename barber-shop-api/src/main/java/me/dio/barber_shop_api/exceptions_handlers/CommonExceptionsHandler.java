package me.dio.barber_shop_api.exceptions_handlers;


import me.dio.barber_shop_api.exceptions.EmptyBodyPayload;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.resource.NoResourceFoundException;


@RestControllerAdvice
public class CommonExceptionsHandler {

    @ExceptionHandler(HttpMessageNotReadableException.class)
    private ResponseEntity<String> isBodyNullOrEmpty(HttpMessageNotReadableException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O corpo da requisição não pode ser vazio ou nulo.");
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    private ResponseEntity<String> bodyInvalid(MethodArgumentNotValidException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Verifique os campos e tente novamente.");
    }

    @ExceptionHandler(EmptyBodyPayload.class)
    private ResponseEntity<String> emptyPayload(EmptyBodyPayload ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Verifique os campos e tente novamente.");
    }

    @ExceptionHandler(NoResourceFoundException.class)
    private ResponseEntity<String> noResourceFound(NoResourceFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Recurso não encontrado.");
    }

}
