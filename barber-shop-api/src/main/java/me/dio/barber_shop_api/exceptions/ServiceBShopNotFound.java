package me.dio.barber_shop_api.exceptions;

public class ServiceBShopNotFound extends RuntimeException {
    public ServiceBShopNotFound() {
      super("Serviço não encontrado.");
    }
}
