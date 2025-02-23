package me.dio.barber_shop_api.repository;

import me.dio.barber_shop_api.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {
    Optional<Booking> findById(String id);

    boolean existsByStartAtLessThanAndEndsAtGreaterThan(LocalTime startAt, LocalTime endsAt);
}
