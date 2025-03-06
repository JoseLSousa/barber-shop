package me.dio.barber_shop_api.exceptions;

public class WorkingDayIsClosed extends RuntimeException {
    public WorkingDayIsClosed() {
        super("O estabelecimento está fechado no dia selecionado");
    }
}
