package me.dio.barber_shop_api.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "barber_shop_options")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BarberShopOption {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    private String description;

    private Integer price;

    @OneToMany(mappedBy = "barberShopOption")
    private List<Booking> bookings;

}
