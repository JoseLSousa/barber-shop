package me.dio.barber_shop_api.model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
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

    private String bookingId;

}
