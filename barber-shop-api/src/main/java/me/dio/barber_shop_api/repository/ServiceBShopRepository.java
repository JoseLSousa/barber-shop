package me.dio.barber_shop_api.repository;

import me.dio.barber_shop_api.model.ServiceBShop;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ServiceBShopRepository extends JpaRepository<ServiceBShop, String> {
    Optional<ServiceBShop> findById(String id);
}
