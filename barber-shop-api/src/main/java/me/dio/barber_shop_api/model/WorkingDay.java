package me.dio.barber_shop_api.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Table(name = "working_days")
@AllArgsConstructor
@NoArgsConstructor
public class WorkingDay {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(nullable = false, unique = true)
    private DayOfWeek dayOfWeek;

    @Column(nullable = false)
    private boolean isOpen;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "workingDay_id", referencedColumnName = "id")
    private List<Shift> shiftList;


}
