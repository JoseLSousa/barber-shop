package me.dio.barber_shop_api.services;

import lombok.AllArgsConstructor;
import me.dio.barber_shop_api.model.DayOfWeek;
import me.dio.barber_shop_api.model.Shift;
import me.dio.barber_shop_api.repository.ShiftRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class ShiftService {
    private final ShiftRepository repository;

    @Transactional
    public Shift createShift(Shift shift) {
        return repository.save(shift);
    }

    public boolean existsShiftConflict(Shift shift, DayOfWeek dayOfWeek) {
        return repository.existsByDayOfWeekAndStartTimeLessThanAndEndTimeGreaterThan(
                dayOfWeek, shift.getEndTime(), shift.getStartTime()) > 0;
    }

    public List<Shift> getShiftsByWorkingDayId(String id){
        return repository.findByWorkingDayId(id);
    }
}
