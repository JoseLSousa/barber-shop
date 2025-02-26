package me.dio.barber_shop_api.dtos.appUser;

import me.dio.barber_shop_api.model.RoleEnum;

public record RequestRegisterDTO(String name, String email, String password, RoleEnum role) {
}
