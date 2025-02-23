package me.dio.barber_shop_api.model;

import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;

@Entity
@Data
@Table(name = "opening_hours")
@AllArgsConstructor
public class OpeningHour {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private DayOfWeek dayOfWeek;

    private LocalTime openingTime;

    private LocalTime closingTime;
    @OneToMany(mappedBy = "openingHour")
    private List<Booking> bookings;
}
