package me.dio.barber_shop_api.exceptions;

public class EmailAlreadyRegistered extends RuntimeException {
    public EmailAlreadyRegistered() {
        super("Já existe cadastro com esse email.");
    }
}
