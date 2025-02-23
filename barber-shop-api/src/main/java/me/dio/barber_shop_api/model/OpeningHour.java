package me.dio.barber_shop_api.model;

import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "opening_hours")
@AllArgsConstructor
@NoArgsConstructor
public class OpeningHour {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private DayOfWeek dayOfWeek;

    private LocalTime openingTime;

    private LocalTime closingTime;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "bookings", referencedColumnName = "id")
    private List<Booking> bookings;

}
