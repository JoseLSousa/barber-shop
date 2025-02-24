package me.dio.barber_shop_api.exceptions;

public class EmptyBodyPayload extends RuntimeException {
    public EmptyBodyPayload() {
        super("Verifique os campos e tente novamente.");
    }
}
