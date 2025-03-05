package me.dio.barber_shop_api.dtos.booking;

import java.time.LocalTime;

public record RequestBookingDTO(String serviceBShopId, String workingDayId, LocalTime time) {

}
