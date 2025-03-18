package me.dio.barber_shop_api.repository;

import me.dio.barber_shop_api.dtos.booking.BookingListDTO;
import me.dio.barber_shop_api.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SuppressWarnings("NullableProblems")
@Repository
public interface BookingRepository extends JpaRepository<Booking, String> {
    Optional<Booking> findById(String id);

    boolean existsByTimeAndDate(LocalTime time, LocalDate date);

    @Query("SELECT b FROM Booking b WHERE b.date = :date")
    List<BookingListDTO> findByDate(@Param("date") LocalDate date);

    @Query("""
            SELECT new me.dio.barber_shop_api.dtos.booking.BookingListDTO
            (b.id, b.time, b.date, b.isDone, wd.dayOfWeek, s.name AS serviceName, u.name AS userName, s.price)
            FROM Booking b
            INNER JOIN WorkingDay wd ON b.workingDay.id = wd.id
            INNER JOIN ServiceBShop s ON b.serviceBShop.id = s.id
            INNER JOIN AppUser u ON b.appUser.id = u.id
            WHERE b.date = :date
            """)
    List<BookingListDTO> findAllBookings(@Param("date") LocalDate date);

    @Query("""
            SELECT new me.dio.barber_shop_api.dtos.booking.BookingListDTO
             (b.id, b.time,b.date, b.isDone, wd.dayOfWeek,
             s.name AS serviceName, null, s.price )
             FROM Booking b
             INNER JOIN WorkingDay wd ON b.workingDay.id = wd.id
             INNER JOIN ServiceBShop s ON b.serviceBShop.id = s.id
             WHERE b.appUser.id = :id
            """)
    List<BookingListDTO> findBookingByAppUserId(@Param("id") String id);

    @Query("SELECT b.time FROM Booking b WHERE b.date =:date")
    ArrayList<LocalTime> findBookedHoursByDate(@Param("date") LocalDate date);
}
