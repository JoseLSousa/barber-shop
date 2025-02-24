package me.dio.barber_shop_api.repository;

import me.dio.barber_shop_api.model.DayOfWeek;
import me.dio.barber_shop_api.model.WorkingDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;


@Repository
public interface WorkingDayRepository extends JpaRepository<WorkingDay, String> {
    Optional<WorkingDay> findByDayOfWeek(DayOfWeek dayOfWeek);

    boolean existsByDayOfWeek(DayOfWeek dayOfWeek);

    boolean existsById(String id);

}
