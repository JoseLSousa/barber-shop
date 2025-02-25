package me.dio.barber_shop_api.dtos.serviceBShop;

import me.dio.barber_shop_api.model.ServiceBShop;

public record RequestServiceBShopDTO(String name, String description, Integer price) {
    public ServiceBShop toEntity() {
        return new ServiceBShop(null, name, description, price);
    }

}
