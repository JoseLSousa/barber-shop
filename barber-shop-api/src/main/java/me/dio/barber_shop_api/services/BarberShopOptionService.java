package me.dio.barber_shop_api.services;

import lombok.AllArgsConstructor;
import me.dio.barber_shop_api.dtos.BarberShopOption.RequestBarberShopOptionDTO;
import me.dio.barber_shop_api.model.BarberShopOption;
import me.dio.barber_shop_api.repository.BarberShopOptionRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.ResourceAccessException;

import java.util.List;

@Service
@AllArgsConstructor
public class BarberShopOptionService {

    private final BarberShopOptionRepository repository;


    public List<BarberShopOption> getAll() {
        return repository.findAll();
    }

    public BarberShopOption getById(String id) {
        return repository.findById(id).orElseThrow(() -> new ResourceAccessException("Not Found"));
    }

    public BarberShopOption save(RequestBarberShopOptionDTO body) {
        BarberShopOption option = new BarberShopOption();
        option.setName(body.name());
        option.setDescription(body.description());
        option.setPrice(body.price());
        return repository.save(option);
    }
}
