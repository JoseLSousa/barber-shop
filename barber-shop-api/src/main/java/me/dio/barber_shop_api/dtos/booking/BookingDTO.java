package me.dio.barber_shop_api.dtos.booking;

import me.dio.barber_shop_api.model.Booking;

import java.time.LocalTime;

public record BookingDTO(String serviceBShopId, String workingDayId, LocalTime time) {
}
