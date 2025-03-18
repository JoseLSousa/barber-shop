package me.dio.barber_shop_api.exceptions;

public class BookingDateInPast extends RuntimeException {
    public BookingDateInPast(String message) {
        super(message);
    }

    public BookingDateInPast() {
        super("O agendamento não pode ser feito para uma data passada.");
    }
}
