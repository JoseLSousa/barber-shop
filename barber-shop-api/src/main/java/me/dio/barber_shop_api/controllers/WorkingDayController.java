package me.dio.barber_shop_api.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import me.dio.barber_shop_api.dtos.workingDay.RequestWorkingDayDTO;
import me.dio.barber_shop_api.model.WorkingDay;
import me.dio.barber_shop_api.services.WorkingDayService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("working-days")
@RequiredArgsConstructor
public class WorkingDayController {


    private final WorkingDayService service;

    @GetMapping
    public ResponseEntity<List<WorkingDay>> getAll() {
        return new ResponseEntity<List<WorkingDay>>(service.findAll(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkingDay> getById(@PathVariable String id) {
        return new ResponseEntity<WorkingDay>(service.findById(id), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<WorkingDay> create(@RequestBody RequestWorkingDayDTO body) {
        return new ResponseEntity<WorkingDay>(service.create(body.toEntity()), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkingDay> update(@PathVariable String id, @Valid @RequestBody RequestWorkingDayDTO body) {

        return new ResponseEntity<WorkingDay>(service.update(id, body.toEntity()), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        System.out.printf(id);
        service.deleteWorkingDay(id);
        return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
    }
}
