package me.dio.barber_shop_api.services;

import lombok.AllArgsConstructor;

import me.dio.barber_shop_api.dtos.shift.ShiftDTO;
import me.dio.barber_shop_api.dtos.workingDay.RequestWorkingDayDTO;
import me.dio.barber_shop_api.exceptions.EmptyBodyPayload;
import me.dio.barber_shop_api.exceptions.WorkingDayAlreadyExists;
import me.dio.barber_shop_api.exceptions.WorkingDayIsClosed;
import me.dio.barber_shop_api.exceptions.WorkingDayNotFound;
import me.dio.barber_shop_api.model.DayOfWeek;
import me.dio.barber_shop_api.model.Shift;
import me.dio.barber_shop_api.model.WorkingDay;
import me.dio.barber_shop_api.repository.WorkingDayRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class WorkingDayService {

    private final WorkingDayRepository repository;

    private final ShiftService shiftService;

    public List<WorkingDay> findAll() {
        return repository.findAll();
    }

    public WorkingDay findById(String id) {
        return repository.findById(id).orElseThrow(WorkingDayNotFound::new);
    }

    public WorkingDay create(RequestWorkingDayDTO body) {
        DayOfWeek day = DayOfWeek.getDayOfWeek(body.dayOfWeek());
        if (repository.existsByDayOfWeek(day)) throw new WorkingDayAlreadyExists();
        if (body.isOpen() && body.shiftList().isEmpty()) throw new EmptyBodyPayload();

        WorkingDay workingDay = body.toEntity();

        List<Shift> shiftList = new ArrayList<>();

        for (ShiftDTO s : body.shiftList()) {
            Shift newShift = s.toEntity(workingDay);
            if (shiftService.existsShiftConflict(newShift, day))
                throw new WorkingDayAlreadyExists();
            shiftList.add(shiftService.createShift(s.toEntity(workingDay)));
        }
        workingDay.setShiftList(shiftList);
        return repository.save(workingDay);
    }

    // Verifica se o dia selecionado está aberto
    public void isOpen(String id) {
        if (!repository.isOpen(id)) throw new WorkingDayIsClosed();
    }

    // Busca o working pelo dia da semana e retorna id
    public String findByDayOfWeek(DayOfWeek day) {
        String id = repository.findByDayOfWeek(day).getId();
        if (id.isEmpty()) throw new WorkingDayNotFound();
        return id;
    }


}
