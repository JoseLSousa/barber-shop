package me.dio.barber_shop_api.dtos.booking;

import java.time.LocalDate;
import java.time.LocalTime;

public record BookingDTO(String serviceBShopId, LocalTime time, LocalDate date, boolean isDone) {
}
