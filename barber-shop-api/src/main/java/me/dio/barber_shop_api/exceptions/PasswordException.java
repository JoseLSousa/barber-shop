package me.dio.barber_shop_api.exceptions;

public class PasswordException extends RuntimeException {
    public PasswordException(){super("A senha não pode ser vazia");}
    public PasswordException(String message) {
        super(message);
    }
}
