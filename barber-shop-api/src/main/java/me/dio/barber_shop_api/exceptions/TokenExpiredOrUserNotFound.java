package me.dio.barber_shop_api.exceptions;

public class TokenExpiredOrUserNotFound extends RuntimeException {
    public TokenExpiredOrUserNotFound(String message) {
        super(message);
    }

    public TokenExpiredOrUserNotFound() {
        super("O token expirou ou usuário não encontrado");
    }
}
