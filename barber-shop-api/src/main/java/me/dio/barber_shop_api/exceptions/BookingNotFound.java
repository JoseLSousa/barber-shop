package me.dio.barber_shop_api.exceptions;

public class BookingNotFound extends RuntimeException {
    public BookingNotFound() {
        super("Agendamento não encontrado.");
    }
}
