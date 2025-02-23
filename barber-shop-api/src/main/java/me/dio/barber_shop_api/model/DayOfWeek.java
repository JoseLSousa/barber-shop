package me.dio.barber_shop_api.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DayOfWeek {
    MONDAY("monday"),
    TUESDAY("tuesday"),
    WEDNESDAY("wednesday"),
    THURSDAY("thursday"),
    FRIDAY("friday"),
    SATURDAY("saturday"),
    SUNDAY("sunday");

    private String day;

}
