package me.dio.barber_shop_api.services;

import lombok.AllArgsConstructor;
import me.dio.barber_shop_api.dtos.OpeningHour.RequestOpeningHourDTO;
import me.dio.barber_shop_api.model.OpeningHour;
import me.dio.barber_shop_api.repository.OpenignHourRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OpeningHourService {

    private final OpenignHourRepository repository;

    public List<OpeningHour> findAll() {
        return repository.findAll();
    }

    public OpeningHour findById(String id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Not Found"));
    }

    public OpeningHour create(RequestOpeningHourDTO body) {
        OpeningHour hour = new OpeningHour();
        hour.setDayOfWeek(body.dayOfWeek());
        hour.setOpeningTime(body.openingHour());
        hour.setClosingTime(body.closingHour());
        return repository.save(hour);
    }

}
