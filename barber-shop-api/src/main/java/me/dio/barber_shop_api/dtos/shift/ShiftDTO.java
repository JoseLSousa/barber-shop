package me.dio.barber_shop_api.dtos.shift;

import me.dio.barber_shop_api.model.Shift;
import me.dio.barber_shop_api.model.WorkingDay;

import java.time.LocalTime;

public record ShiftDTO(LocalTime startTime, LocalTime endTime) {
    public Shift toEntity(WorkingDay workingDayId){
        return new Shift(null, startTime, endTime);
    }
}
