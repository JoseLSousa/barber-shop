package me.dio.barber_shop_api.dtos.shift;

import me.dio.barber_shop_api.model.Shift;
import me.dio.barber_shop_api.model.WorkingDay;

import java.time.LocalTime;

public record ShiftDTO(String id,LocalTime startTime, LocalTime endTime) {
    public Shift toEntity(WorkingDay workingDay){
        return new Shift(id, startTime, endTime, workingDay);
    }
}
