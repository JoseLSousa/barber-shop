package me.dio.barber_shop_api.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DayOfWeek {
    SEGUNDA(0),
    TERCA(1),
    QUARTA(2),
    QUINTA(3),
    SEXTA(4),
    SABADO(5),
    DOMINGO(6);


    private final int dayNumber;

    public static DayOfWeek getDayOfWeek(int dayNumber) {
        for(DayOfWeek day : DayOfWeek.values()) {
            if(day.dayNumber == dayNumber) {
                return day;
            }
        }
        throw new IllegalArgumentException("Invalid day number: " + dayNumber);
    }

}
