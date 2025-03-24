package me.dio.barber_shop_api.services;

import lombok.RequiredArgsConstructor;
import me.dio.barber_shop_api.dtos.shift.ShiftDTO;
import me.dio.barber_shop_api.dtos.workingDay.RequestWorkingDayDTO;
import me.dio.barber_shop_api.exceptions.EmptyBodyPayload;
import me.dio.barber_shop_api.exceptions.WorkingDayAlreadyExists;
import me.dio.barber_shop_api.exceptions.WorkingDayIsClosed;
import me.dio.barber_shop_api.exceptions.WorkingDayNotFound;
import me.dio.barber_shop_api.model.Shift;
import me.dio.barber_shop_api.model.WorkingDay;
import me.dio.barber_shop_api.repository.WorkingDayRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WorkingDayService {

    private final WorkingDayRepository repository;
    private final ShiftService shiftService;
    private final WorkingDayRepository workingDayRepository;

    @Transactional(readOnly = true)
    public List<WorkingDay> findAll() {
        return repository.findAllByOrderByDayOfWeekAsc();
    }

    @Transactional(readOnly = true)
    public WorkingDay findById(String id) {
        return repository.findById(id).orElseThrow(WorkingDayNotFound::new);
    }

    @Transactional
    public WorkingDay create(RequestWorkingDayDTO body) {
        DayOfWeek day = DayOfWeek.of(body.dayOfWeek().getValue());

        if (repository.existsByDayOfWeek(day)) throw new WorkingDayAlreadyExists();
        if (body.isOpen() && body.shiftList().isEmpty()) throw new EmptyBodyPayload();

        WorkingDay wd = new WorkingDay();
        wd.setDayOfWeek(day);
        wd.setOpen(body.isOpen());

        List<Shift> shiftList = new ArrayList<>();
        if (!body.shiftList().isEmpty()) {
            for (ShiftDTO s : body.shiftList()) {
                if (s.startTime() == null || s.endTime() == null) throw new EmptyBodyPayload();
                Shift shift = new Shift();
                shift.setStartTime(s.startTime());
                shift.setEndTime(s.endTime());
                shift.setWorkingDay(wd);
                shiftList.add(shiftService.createShift(shift));
            }
        }

        wd.setShiftList(shiftList);

        return workingDayRepository.save(wd);
    }


    @Transactional
    public WorkingDay update(String id, RequestWorkingDayDTO body) {
        WorkingDay existingDay = findById(id);
        existingDay.setDayOfWeek(body.dayOfWeek());
        existingDay.setOpen(body.isOpen());
        List<Shift> existingShifts = shiftService.getShiftsByWorkingDayId(id);

        if (existingShifts.size() <= body.shiftList().size()) {
            for (int i = 0; i < body.shiftList().size(); i++) {
                Shift shift = body.shiftList().get(i).toEntity(existingDay);

                if (i < existingShifts.size() && existingShifts.get(i).getId().equals(shift.getId())) {
                    shift.setStartTime(body.shiftList().get(i).startTime());
                    shift.setEndTime(body.shiftList().get(i).endTime());
                    shiftService.saveShift(shift);
                } else {
                    createShiftWithValidation(body.shiftList().get(i), existingDay, existingDay.getDayOfWeek());
                }
            }
        } else {
            for (Shift existingShift : existingShifts) {
                if (body.shiftList().stream().noneMatch(s -> s.id().equals(existingShift.getId()))) {
                    shiftService.deleteShift(existingShift.getId());
                }
            }
        }
        return repository.save(existingDay);
    }

    @Transactional(readOnly = true)
    public void isOpen(String id) {
        if (!repository.existsByIdAndIsOpenTrue(id)) {
            throw new WorkingDayIsClosed();
        }
    }

    @Transactional(readOnly = true)
    public WorkingDay findByDayOfWeekEnum(DayOfWeek day) {
        return Optional.ofNullable(repository.findByDayOfWeek(day))
                .orElseThrow(WorkingDayNotFound::new);
    }

    @Transactional(readOnly = true)
    public String findByDayOfWeek(int day) {
        return Optional.ofNullable(repository.findByDayOfWeek(DayOfWeek.of(day)))
                .map(WorkingDay::getId)
                .orElseThrow(WorkingDayNotFound::new);
    }

    @Transactional(readOnly = true)
    public List<Integer> getOpenDays() {
        return repository.findDayOfWeekByIsOpenTrue();
    }

    public void delete(String id) {
        if (!repository.existsById(id)) throw new WorkingDayNotFound();
        repository.deleteById(id);
    }

    private Shift createShiftWithValidation(ShiftDTO shiftDTO, WorkingDay workingDay, DayOfWeek day) {
        Shift newShift = shiftDTO.toEntity(workingDay);
        if (shiftService.existsShiftConflict(newShift, day)) {
            throw new WorkingDayAlreadyExists();
        }
        return shiftService.createShift(newShift);
    }
}