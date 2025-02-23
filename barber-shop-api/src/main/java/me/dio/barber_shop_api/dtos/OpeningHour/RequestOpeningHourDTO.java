package me.dio.barber_shop_api.dtos.OpeningHour;

import me.dio.barber_shop_api.model.DayOfWeek;

import java.time.LocalTime;

public record RequestOpeningHourDTO(DayOfWeek dayOfWeek, LocalTime openingHour, LocalTime closingHour) {
}
