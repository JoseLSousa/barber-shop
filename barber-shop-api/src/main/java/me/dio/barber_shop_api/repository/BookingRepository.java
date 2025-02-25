package me.dio.barber_shop_api.repository;

import me.dio.barber_shop_api.dtos.booking.RequestListByUserDTO;
import me.dio.barber_shop_api.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {
    Optional<Booking> findById(String id);

    boolean existsByTime(LocalTime time);

    @NativeQuery(value = "SELECT " +
            "bookings.id, " +
            "bookings.time, " +
            "barber_shop_services.name AS service_name, " +
            "working_days.day_of_week AS day_of_week " +
            "FROM bookings " +
            "RIGHT JOIN barber_shop_services " +
            "ON barber_shop_services.id = bookings.servicebshop_id " +
            "INNER JOIN working_days " +
            "ON working_days.id = bookings.working_day_id " +
            "WHERE bookings.app_user_id = :id")
    List<RequestListByUserDTO> findBookingByAppUserId(@Param("id") String id);
}
