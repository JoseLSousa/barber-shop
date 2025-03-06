package me.dio.barber_shop_api.model;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum DayOfWeek {
    SEGUNDA("segunda"),
    TERCA("terça"),
    QUARTA("quarta"),
    QUINTA("quinta"),
    SEXTA("sexta"),
    SABADO("sabado"),
    DOMINGO("domingo");


    private String day;

}
