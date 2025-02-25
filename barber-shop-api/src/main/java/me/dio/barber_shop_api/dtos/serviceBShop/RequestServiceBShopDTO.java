package me.dio.barber_shop_api.dtos.ServiceBShop;

import jakarta.validation.constraints.NotNull;
import me.dio.barber_shop_api.model.ServiceBShop;

import java.time.Duration;

public record RequestServiceBShopDTO(String name, String description, Integer price) {
    public ServiceBShop toEntity() {
        return new ServiceBShop(null, name, description, price);
    }

}
