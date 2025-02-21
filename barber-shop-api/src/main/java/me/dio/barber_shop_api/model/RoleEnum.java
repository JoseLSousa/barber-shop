package me.dio.barber_shop_api.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RoleEnum {
    ADMIN("admin"),
    USER("user");

    private String role;


    public String getRole(){
        return role;
    }
}
