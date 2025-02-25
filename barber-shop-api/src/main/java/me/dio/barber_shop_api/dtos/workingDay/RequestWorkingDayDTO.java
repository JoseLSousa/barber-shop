package me.dio.barber_shop_api.dtos.workingDay;

import jakarta.validation.constraints.NotNull;
import me.dio.barber_shop_api.model.WorkingDay;

import java.time.LocalDate;
import java.time.LocalTime;

public record RequestWorkingDayDTO(
        @NotNull(message = "O dia da semana não pode ser nulo")
        LocalDate dayOfMonth,
        @NotNull(message = "O horário de abertura não pode ser nulo")
        LocalTime openingTime,
        @NotNull(message = "O horário de fechamento não pode ser nulo")
        LocalTime closingTime) {

    public WorkingDay toEntity(){
        return new WorkingDay(null,dayOfMonth,openingTime,closingTime);
    }
}
