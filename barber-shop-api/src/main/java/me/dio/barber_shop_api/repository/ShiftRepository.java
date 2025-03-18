package me.dio.barber_shop_api.repository;

import me.dio.barber_shop_api.model.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Repository
public interface ShiftRepository extends JpaRepository<Shift, String> {

    @Query(value = """
            SELECT COUNT(*)
            FROM working_days
            RIGHT JOIN shifts ON working_days.id = shifts.working_day_id
            WHERE working_days.day_of_week = :dayOfWeek
            AND shifts.start_time < :endTime
            AND shifts.end_time > :startTime
            """, nativeQuery = true)
    Integer existsByDayOfWeekAndStartTimeLessThanAndEndTimeGreaterThan(
            @Param("dayOfWeek") DayOfWeek dayOfWeek, @Param("endTime") LocalTime endTime, @Param("startTime") LocalTime startTime);

    @Query(value = """
            SELECT * FROM shifts WHERE working_day_id = :id
            """, nativeQuery = true)
    List<Shift> findByWorkingDayId(@Param("id") String id);

    List<Shift> findByWorkingDayDayOfWeek(DayOfWeek day);
}
