package me.dio.barber_shop_api.dtos.booking;


import me.dio.barber_shop_api.model.Booking;

import java.time.LocalTime;

public record ResponseBookingDTO(
        String id,
        LocalTime time,
        String serviceBShopId,
        String workingDayId) {

    public static ResponseBookingDTO fromEntity(Booking booking) {
        return new ResponseBookingDTO(
                booking.getId(),
                booking.getTime(),
                booking.getServiceBShop().getId(),
                booking.getWorkingDay().getId()
        );
    }
}

