package me.dio.barber_shop_api.dtos.booking;

import java.time.LocalDate;
import java.time.LocalTime;


public record RequestBookingListByUserDTO(
        String bookingId,
        LocalTime time,
        LocalDate dayOfMonth,
        String serviceName,
        Integer price) {
}

