package me.dio.barber_shop_api.controllers;

import lombok.AllArgsConstructor;
import me.dio.barber_shop_api.dtos.BarberShopOption.RequestBarberShopOptionDTO;
import me.dio.barber_shop_api.model.BarberShopOption;
import me.dio.barber_shop_api.services.BarberShopOptionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/barber-shop-options")
@AllArgsConstructor
public class BarberShopOptionController {

    private final BarberShopOptionService service;

    @GetMapping
    public List<BarberShopOption> getAll() {
        return service.getAll();
    }
    @GetMapping("{id}")
    public BarberShopOption getById(@PathVariable String id){
        return service.getById(id);
    }

    @PostMapping
    public BarberShopOption create(@RequestBody RequestBarberShopOptionDTO body){
        return service.save(body);
    }
}
