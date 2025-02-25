package me.dio.barber_shop_api.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "barber_shop_services")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ServiceBShop {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    private String name;

    private String description;

    private Integer price;


}
