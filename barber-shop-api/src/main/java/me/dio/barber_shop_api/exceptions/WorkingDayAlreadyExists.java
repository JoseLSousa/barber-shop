package me.dio.barber_shop_api.exceptions;

public class WorkingDayAlreadyExists extends RuntimeException {

    public WorkingDayAlreadyExists() {
        super("Horário de funcionamento já cadastrado para esse dia.");
    }

    public WorkingDayAlreadyExists(String message) {
        super(message);
    }
}
