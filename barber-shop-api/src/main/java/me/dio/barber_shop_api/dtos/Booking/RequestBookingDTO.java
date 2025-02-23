package me.dio.barber_shop_api.dtos.Booking;

import java.time.LocalTime;

public record RequestBookingDTO(LocalTime startAt, LocalTime endsAt, String openingHourId, String barberShopOptionId) {
}
