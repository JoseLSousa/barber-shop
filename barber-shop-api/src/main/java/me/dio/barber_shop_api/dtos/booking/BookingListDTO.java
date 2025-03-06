package me.dio.barber_shop_api.dtos.booking;

import me.dio.barber_shop_api.model.DayOfWeek;
import java.time.LocalTime;


public record BookingListDTO(
        String bookingId,
        LocalTime time,
        DayOfWeek dayOfWeek,
        String serviceName,
        Integer price) {
}

