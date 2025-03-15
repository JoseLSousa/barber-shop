package me.dio.barber_shop_api.services;

import lombok.RequiredArgsConstructor;
import me.dio.barber_shop_api.dtos.shift.ShiftDTO;
import me.dio.barber_shop_api.dtos.workingDay.RequestWorkingDayDTO;
import me.dio.barber_shop_api.exceptions.*;
import me.dio.barber_shop_api.model.DayOfWeek;
import me.dio.barber_shop_api.model.Shift;
import me.dio.barber_shop_api.model.WorkingDay;
import me.dio.barber_shop_api.repository.WorkingDayRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WorkingDayService {

    private final WorkingDayRepository repository;
    private final ShiftService shiftService;

    @Transactional(readOnly = true)
    public List<WorkingDay> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public WorkingDay findById(String id) {
        return repository.findById(id).orElseThrow(WorkingDayNotFound::new);
    }

    @Transactional
    public WorkingDay create(RequestWorkingDayDTO body) {
        DayOfWeek day = DayOfWeek.getDayOfWeek(body.dayOfWeek());

        if (repository.existsByDayOfWeek(day)) {
            throw new WorkingDayAlreadyExists();
        }

        if (body.isOpen() && body.shiftList().isEmpty()) {
            throw new EmptyBodyPayload();
        }

        WorkingDay workingDay = body.toEntity();
        if (!body.shiftList().isEmpty()) {
            List<Shift> shiftList = body.shiftList().stream()
                    .map(s -> createShiftWithValidation(s, workingDay, day))
                    .collect(Collectors.toList());
            workingDay.setShiftList(shiftList);
        }
        return repository.save(workingDay);
    }

    @Transactional
    public WorkingDay update(String id, RequestWorkingDayDTO body) {
        WorkingDay existingDay = findById(id);
        existingDay.setDayOfWeek(DayOfWeek.getDayOfWeek(body.dayOfWeek()));
        existingDay.setOpen(body.isOpen());

        List<Shift> existingShifts = existingDay.getShiftList();
        List<ShiftDTO> newShifts = body.shiftList();

        // Atualizar ou adicionar turnos
        for (ShiftDTO shiftDTO : newShifts) {
            Shift shift = shiftDTO.toEntity(existingDay);
            if (shift.getId() == null) {
                shiftService.createShift(shift);
                existingShifts.add(shift);
            } else {
                Shift existingShift = shiftService.getShiftById(shift.getId());
                if (existingShift != null) {
                    existingShift.setStartTime(shift.getStartTime());
                    existingShift.setEndTime(shift.getEndTime());
                    existingShift.setWorkingDay(existingDay);
                    shiftService.saveShift(existingShift);
                } else {
                    shiftService.createShift(shift);
                    existingShifts.add(shift);
                }
            }
        }

    // Remover turnos que não estão na nova lista
    existingShifts.removeIf(shift -> newShifts.stream()
            .noneMatch(dto -> dto.id() != null && dto.id().equals(shift.getId())));

    existingDay.setShiftList(existingShifts);
    return repository.save(existingDay);
}

    @Transactional(readOnly = true)
    public void isOpen(String id) {
        if (!repository.isOpen(id)) {
            throw new WorkingDayIsClosed();
        }
    }

    @Transactional(readOnly = true)
    public String findByDayOfWeek(DayOfWeek day) {
        return Optional.ofNullable(repository.findByDayOfWeek(day))
                .map(WorkingDay::getId)
                .orElseThrow(WorkingDayNotFound::new);
    }

    private Shift createShiftWithValidation(ShiftDTO shiftDTO, WorkingDay workingDay, DayOfWeek day) {
        Shift newShift = shiftDTO.toEntity(workingDay);
        if (shiftService.existsShiftConflict(newShift, day)) {
            throw new WorkingDayAlreadyExists();
        }
        return shiftService.createShift(newShift);
    }
}