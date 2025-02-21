package me.dio.barber_shop_api.dtos;

import me.dio.barber_shop_api.model.RoleEnum;

public record RegisterDTO(String name, String email, String password, RoleEnum role) {

}
