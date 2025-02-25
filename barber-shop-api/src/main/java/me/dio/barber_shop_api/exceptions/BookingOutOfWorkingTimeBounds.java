package me.dio.barber_shop_api.exceptions;

public class BookingOutOfWorkingTimeBounds extends RuntimeException {
    public BookingOutOfWorkingTimeBounds() {
        super("A hora selecionada está fora do horário de funcionamento ou horário inexistente.");
    }
}
