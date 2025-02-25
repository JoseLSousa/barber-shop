package me.dio.barber_shop_api.exceptions;

public class BookingAlreadyExists extends RuntimeException {

    public BookingAlreadyExists() {
        super("Já existe um agendamento nesse horário.");
    }

    public BookingAlreadyExists(String message) {
        super(message);
    }
}
