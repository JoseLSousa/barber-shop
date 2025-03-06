package me.dio.barber_shop_api.dtos.appUser;

public record ResponseLoginDTO(String token, String name) {
    public static ResponseLoginDTO toDto(String token, String name) {
        return new ResponseLoginDTO(token, name);
    }
}
