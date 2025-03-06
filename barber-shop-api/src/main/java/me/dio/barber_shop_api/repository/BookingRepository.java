package me.dio.barber_shop_api.repository;

import me.dio.barber_shop_api.dtos.booking.BookingListDTO;
import me.dio.barber_shop_api.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {
    Optional<Booking> findById(String id);

    boolean existsByTime(LocalTime time);

    @Query("SELECT new me.dio.barber_shop_api.dtos.booking.BookingListDTO (b.id, b.time, wd.dayOfWeek, " +
            "s.name AS serviceName, s.price )" +
            "FROM Booking b " +
            "INNER JOIN WorkingDay wd ON b.workingDay.Id = wd.id " +
            "INNER JOIN ServiceBShop s ON b.serviceBShop.Id = s.id " +
            "WHERE b.appUser.Id = :id")
    List<BookingListDTO> findBookingByAppUserId(@Param("id") String id);

    @Query("SELECT b.time FROM Booking b WHERE b.workingDay.id =:id")
    ArrayList<LocalTime> findBookingsByWorkingDayId(@Param("id") String id);
}
