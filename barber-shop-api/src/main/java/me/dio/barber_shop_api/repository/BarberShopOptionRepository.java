package me.dio.barber_shop_api.repository;

import me.dio.barber_shop_api.model.BarberShopOption;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BarberShopOptionRepository extends JpaRepository<BarberShopOption, String> {
    Optional<BarberShopOption> findById(String id);
}
