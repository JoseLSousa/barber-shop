package me.dio.barber_shop_api.model;

import java.time.LocalTime;
import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Booking {
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(nullable = false)
    @Id
    private String id;

    @Column(nullable = false, name = "start_at")
    private LocalTime startAt;
    
    
    @Column(nullable = false, name = "ends_at")
    private LocalTime endsAt;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "app_user_id", nullable = false)
    private AppUser appUser;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "openingHour_id", nullable = false)
    private OpeningHour openingHour;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "barberShopOption_id", nullable = false)
    private BarberShopOption barberShopOption;

    
}
