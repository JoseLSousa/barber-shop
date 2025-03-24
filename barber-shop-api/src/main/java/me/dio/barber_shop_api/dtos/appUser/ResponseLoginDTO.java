package me.dio.barber_shop_api.dtos.appUser;

public record ResponseLoginDTO(String token, String name, String role) {
    public static ResponseLoginDTO toDto(String token, String name, String role) {
        return new ResponseLoginDTO(token, name, role);
    }
}
