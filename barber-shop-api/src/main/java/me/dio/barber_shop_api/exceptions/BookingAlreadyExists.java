package me.dio.barber_shop_api.exceptions;

public class BookingAlreadyExists extends RuntimeException {

    public BookingAlreadyExists() {
        super("Agendamento já existe");
    }

    public BookingAlreadyExists(String message) {
        super(message);
    }
}
