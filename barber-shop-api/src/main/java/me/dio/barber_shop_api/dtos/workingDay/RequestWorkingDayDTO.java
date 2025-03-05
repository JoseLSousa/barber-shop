package me.dio.barber_shop_api.dtos.workingDay;


import me.dio.barber_shop_api.dtos.shift.ShiftDTO;
import me.dio.barber_shop_api.model.DayOfWeek;
import me.dio.barber_shop_api.model.WorkingDay;

import java.util.List;

public record RequestWorkingDayDTO(
        DayOfWeek dayOfWeek,
        boolean isOpen,
        List<ShiftDTO> shiftList) {

    public WorkingDay toEntity() {
        return new WorkingDay(null, dayOfWeek, isOpen, null);
    }
}
