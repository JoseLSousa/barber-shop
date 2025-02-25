package me.dio.barber_shop_api.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import me.dio.barber_shop_api.dtos.serviceBShop.RequestServiceBShopDTO;
import me.dio.barber_shop_api.model.ServiceBShop;
import me.dio.barber_shop_api.services.ServiceBShopService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/services-barber-shop")
@AllArgsConstructor
public class ServiceBShopController {

    private final ServiceBShopService service;

    @GetMapping
    public List<ServiceBShop> getAll() {
        return service.getAll();
    }

    @GetMapping("{id}")
    public ServiceBShop getById(@PathVariable String id) {
        return service.getById(id);
    }

    @PostMapping
    public ServiceBShop create(@RequestBody RequestServiceBShopDTO body) {
        return service.save(body);
    }

    @PutMapping("{id}")
    public ServiceBShop update(@PathVariable String id, @Valid @RequestBody RequestServiceBShopDTO body) {
        return service.update(id, body);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
