package me.dio.barber_shop_api.repository;

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
    @Query("""
            SELECT wd FROM WorkingDay wd WHERE wd.id = :id
            AND :time BETWEEN wd.openingTime AND wd.closingTime
            """)
    Optional<WorkingDay> findValidWorkingDay(@Param("id") String id, @Param("time") LocalTime time);

    boolean existsByDayOfMonth(LocalDate dayOfMonth);

    boolean existsById(String id);

}
