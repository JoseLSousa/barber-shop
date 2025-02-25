package me.dio.barber_shop_api.services;

import lombok.AllArgsConstructor;

import me.dio.barber_shop_api.exceptions.EmptyBodyPayload;
import me.dio.barber_shop_api.exceptions.WorkingDayAlreadyExists;
import me.dio.barber_shop_api.exceptions.WorkingDayNotFound;
import me.dio.barber_shop_api.model.DayOfWeek;
import me.dio.barber_shop_api.model.WorkingDay;
import me.dio.barber_shop_api.repository.WorkingDayRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class WorkingDayService {

    private final WorkingDayRepository repository;

    public List<WorkingDay> findAll() {
        return repository.findAll();
    }

    public WorkingDay findById(String id) {
        return repository.findById(id).orElseThrow(WorkingDayNotFound::new);
    }

    public WorkingDay create(WorkingDay body) {
        if (workingDayExists(body.getDayOfWeek())) throw new WorkingDayAlreadyExists();
        return repository.save(body);
    }

    public WorkingDay update(String id, WorkingDay workingDay) {
        if (!repository.existsById(id)) throw new WorkingDayNotFound();
        if (bodyIsNullOrEmpty(workingDay)) throw new EmptyBodyPayload();
        workingDay.setId(id);
        return repository.save(workingDay);
    }

    public void deleteWorkingDay(String id) {
        WorkingDay workingDay = repository.findById(id).orElseThrow(WorkingDayNotFound::new);
        System.out.printf(workingDay.toString());
        repository.delete(workingDay);
    }

    private boolean bodyIsNullOrEmpty(WorkingDay body) {
        return (body == null) ? true : false;
    }

    private boolean workingDayExists(DayOfWeek dayOfWeek) {
        return repository.existsByDayOfWeek(dayOfWeek);
    }

    private boolean workingDayExistsById(String id) {
        return repository.existsById(id);
    }

}
