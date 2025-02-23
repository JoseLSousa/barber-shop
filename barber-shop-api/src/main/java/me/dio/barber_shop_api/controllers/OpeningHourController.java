package me.dio.barber_shop_api.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import me.dio.barber_shop_api.dtos.OpeningHour.RequestOpeningHourDTO;
import me.dio.barber_shop_api.model.OpeningHour;
import me.dio.barber_shop_api.services.OpeningHourService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("opening-hours")
@AllArgsConstructor
public class OpeningHourController {

    private final OpeningHourService service;

    @GetMapping
    public List<OpeningHour> getAll() {
        return service.findAll();
    }

    @GetMapping("/{id}")
    public OpeningHour getById(@PathVariable String id) {
        return service.findById(id);
    }

    @PostMapping
    public OpeningHour create(@RequestBody RequestOpeningHourDTO body) {

        return service.create(body);
    }
}
