package me.dio.barber_shop_api.services;

import lombok.AllArgsConstructor;
import me.dio.barber_shop_api.dtos.serviceBShop.RequestServiceBShopDTO;
import me.dio.barber_shop_api.exceptions.ServiceBShopNotFound;
import me.dio.barber_shop_api.model.ServiceBShop;
import me.dio.barber_shop_api.repository.ServiceBShopRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ServiceBShopService {

    private final ServiceBShopRepository service;

    public List<ServiceBShop> getAll() {
        return service.findAll();
    }


    public ServiceBShop getById(String id) {
        return service.findById(id).orElseThrow(ServiceBShopNotFound::new);
    }

    public ServiceBShop save(RequestServiceBShopDTO body) {
        return service.save(body.toEntity());
    }

    public ServiceBShop update(String id, RequestServiceBShopDTO body) {
        serviceExists(id);
        ServiceBShop updated;
        updated = body.toEntity();
        updated.setId(id);
        return service.save(updated);
    }

    public void delete(String id) {
        serviceExists(id);
        service.deleteById(id);
    }

    private void serviceExists(String id) {
        service.findById(id).orElseThrow(ServiceBShopNotFound::new);
    }
}
