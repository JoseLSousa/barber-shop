package me.dio.barber_shop_api.repository;

import me.dio.barber_shop_api.model.DayOfWeek;
import me.dio.barber_shop_api.model.WorkingDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Optional;


@Repository
public interface WorkingDayRepository extends JpaRepository<WorkingDay, String> {
    Optional<WorkingDay> findById(String id);

    @Query(value = """
            SELECT EXISTS (SELECT id FROM working_days
            WHERE id = :id AND is_open = TRUE AS result)
            """, nativeQuery = true)
    boolean isOpen(@Param("id") String id);

    boolean existsByDayOfWeek(DayOfWeek dayOfWeek);

    WorkingDay findByDayOfWeek(DayOfWeek dayOfWeek);

    boolean existsById(String id);

}
