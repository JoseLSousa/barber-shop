package me.dio.barber_shop_api.repository;

import me.dio.barber_shop_api.model.DayOfWeek;
import me.dio.barber_shop_api.model.OpeningHour;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;


@Repository
public interface OpeningHourRepository extends JpaRepository<OpeningHour, String> {
    Optional<OpeningHour> findById(String id);

    List<OpeningHour> findByDayOfWeek(DayOfWeek dayOfWeek);

    boolean existsByOpeningTimeLessThanEqualAndClosingTimeGreaterThanEqual(LocalTime openingTime, LocalTime closingTime);
}
