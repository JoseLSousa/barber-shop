package me.dio.barber_shop_api.exceptions;

public class WorkingDayNotFound extends RuntimeException {

    public WorkingDayNotFound() {
        super("Horário de funcionamento não encontrado.");
    }

    public WorkingDayNotFound(String message) {
        super(message);
    }
}
