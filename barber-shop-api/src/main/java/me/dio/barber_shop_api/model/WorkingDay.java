package me.dio.barber_shop_api.model;

import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import me.dio.barber_shop_api.dtos.WorkingDay.RequestWorkingDayDTO;

@Entity
@Data
@Table(name = "working_days")
@AllArgsConstructor
@NoArgsConstructor
public class WorkingDay {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private DayOfWeek dayOfWeek;

    @Column(nullable = false)
    private LocalTime openingTime;

    @Column(nullable = false)
    private LocalTime closingTime;


}
