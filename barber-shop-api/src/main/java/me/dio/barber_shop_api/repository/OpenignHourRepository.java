package me.dio.barber_shop_api.repository;

import lombok.AllArgsConstructor;

import lombok.RequiredArgsConstructor;
import me.dio.barber_shop_api.model.OpeningHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface OpenignHourRepository extends JpaRepository<OpeningHour, String> {
    Optional<OpeningHour> findById(String id);
}
