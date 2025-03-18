package me.dio.barber_shop_api.dtos.booking;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;


public record BookingListDTO(
        String bookingId,
        LocalTime time,
        LocalDate date,
        boolean isDone,
        DayOfWeek dayOfWeek,
        String serviceName,
        String Username,
        Integer price) {
}

