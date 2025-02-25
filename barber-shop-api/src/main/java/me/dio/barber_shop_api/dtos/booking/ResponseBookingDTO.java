package me.dio.barber_shop_api.dtos.Booking;


import me.dio.barber_shop_api.model.BarberShopOption;
import me.dio.barber_shop_api.model.Booking;

import java.time.LocalTime;
import java.util.List;

public record ResponseBookingDTO(String id, LocalTime startAt, LocalTime endsAt,
                                 BarberShopOption barberShopOptionId) {
    public ResponseBookingDTO(Booking booking) {
        this(booking.getId(), booking.getStartAt(), booking.getEndsAt(), booking.getBarberShopOption());
    }
}
