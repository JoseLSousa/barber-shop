package me.dio.barber_shop_api.model;

import java.time.LocalTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

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

    @Column(nullable = false, name = "start_at")
    private LocalTime startAt;

    @Column(nullable = false, name = "ends_at")
    private LocalTime endsAt;

    @Column(name = "AppUser_id", nullable = false)
    private String appUserId;

    @Column(name = "openingHour_id", nullable = false)
    private String openingHourId;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "barberShopOption_id", referencedColumnName = "id")
    private BarberShopOption barberShopOption;


}
