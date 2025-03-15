package me.dio.barber_shop_api.repository;

import me.dio.barber_shop_api.model.DayOfWeek;
import me.dio.barber_shop_api.model.WorkingDay;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Repository
public interface WorkingDayRepository extends JpaRepository<WorkingDay, String> {
    Optional<WorkingDay> findById(String id);

    boolean existsByIdAndIsOpenTrue(String id);

    boolean existsByDayOfWeek(DayOfWeek dayOfWeek);

    WorkingDay findByDayOfWeek(DayOfWeek dayOfWeek);

    boolean existsById(String id);

    @Query("SELECT w.dayOfWeek FROM WorkingDay w WHERE w.isOpen = true")
    List<Integer> findDayOfWeekByIsOpenTrue();



}
