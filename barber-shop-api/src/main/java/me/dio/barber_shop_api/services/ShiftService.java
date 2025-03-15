package me.dio.barber_shop_api.services;

import lombok.AllArgsConstructor;
import me.dio.barber_shop_api.dtos.shift.ShiftDTO;
import me.dio.barber_shop_api.exceptions.WorkingDayAlreadyExists;
import me.dio.barber_shop_api.model.DayOfWeek;
import me.dio.barber_shop_api.model.Shift;
import me.dio.barber_shop_api.model.WorkingDay;
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

    public boolean existsShiftConflict(Shift newShift, DayOfWeek day) {
        List<Shift> shifts = getShiftsByDayOfWeek(day);
        for (Shift shift : shifts) {
            if (shift.getStartTime().isBefore(newShift.getEndTime()) && newShift.getStartTime().isBefore(shift.getEndTime())) {
                return true;
            }
        }
        return false;
    }

    public List<Shift> getShiftsByDayOfWeek(DayOfWeek day) {
        return repository.findByWorkingDayDayOfWeek(day);
    }

    public List<Shift> getShiftsByWorkingDayId(String id){
        return repository.findByWorkingDayId(id);
    }

    public Shift getShiftById(String id) {
        return repository.findById(id).orElse(null);
    }

    public void saveShift(Shift shift) {
        repository.save(shift);
    }

    public void deleteShift(String id) {
        repository.deleteById(id);
    }
}
