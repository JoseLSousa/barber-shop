package me.dio.barber_shop_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@SuppressWarnings("JpaDataSourceORMInspection")
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "bookings")
public class Booking {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "appUser_id", nullable = false)
    private AppUser appUser;

    @ManyToOne
    @JoinColumn(name = "serviceBShop_id", nullable = false)
    private ServiceBShop serviceBShop;

    @ManyToOne
    @JoinColumn(name = "workingDay_id", nullable = false)
    private WorkingDay workingDay;

    @Column(nullable = false)
    private LocalTime time;

    @Column(nullable = false)
    private LocalDate date;

    @Column(nullable = false)
    private boolean isDone;

}
