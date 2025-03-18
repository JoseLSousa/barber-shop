package me.dio.barber_shop_api.services;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import me.dio.barber_shop_api.dtos.booking.BookingDTO;
import me.dio.barber_shop_api.dtos.booking.BookingListDTO;
import me.dio.barber_shop_api.dtos.booking.ResponseBookingDTO;
import me.dio.barber_shop_api.exceptions.BookingAlreadyExists;
import me.dio.barber_shop_api.exceptions.BookingDateInPast;
import me.dio.barber_shop_api.exceptions.BookingNotFound;
import me.dio.barber_shop_api.exceptions.BookingOutOfWorkingTimeBounds;
import me.dio.barber_shop_api.model.Booking;
import me.dio.barber_shop_api.model.Shift;
import me.dio.barber_shop_api.repository.BookingRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final WorkingDayService workingDayService;
    private final ShiftService shiftService;
    private final TokenService tokenService;
    private final AppUserService appUserService;
    private final ServiceBShopService serviceBShopService;

    public Booking getById(String id) {
        return bookingRepository.findById(id)
                .orElseThrow(BookingNotFound::new);
    }

    public List<BookingListDTO> getBookingsByParams(LocalDate date, String direction) {
        LocalDate validDate = date == null ? LocalDate.now() : date;

        if (direction.equalsIgnoreCase("DESC")) {
            List<BookingListDTO> listReversed = bookingRepository.findAllBookings(validDate);
            Collections.reverse(listReversed);
            return listReversed;
        }

        return bookingRepository.findAllBookings(validDate);
    }

    public List<BookingListDTO> listByUser(HttpServletRequest request) {
        String email = getEmailFromToken(request);
        return bookingRepository.findBookingByAppUserId(appUserService.getUserByEmail(email).getId());
    }

    public ArrayList<LocalTime> getAvailableHours(LocalDate date) {
        ArrayList<LocalTime> availableHours = new ArrayList<>();
        if (date.isBefore(LocalDate.now())) return availableHours;
        List<LocalTime> alreadyBookedTimes = bookingRepository.findBookedHoursByDate(date);
        for (LocalTime lt : verifyHours(date.getDayOfWeek())) {
            if (!alreadyBookedTimes.contains(lt)) {
                availableHours.add(lt);
            }
        }

        return availableHours;
    }

    public ResponseBookingDTO createBooking(BookingDTO body, HttpServletRequest request) {
        if (bookingRepository.existsByTimeAndDate(body.time(), body.date()))
            throw new BookingAlreadyExists();

        validateSelectedDayAndHours(body.date(), body.time());

        if (!verifyHours(body.date().getDayOfWeek()).contains(body.time()))
            throw new BookingOutOfWorkingTimeBounds();

        Booking newBooking = new Booking();
        newBooking.setAppUser(appUserService.getUserByEmail(getEmailFromToken(request)));
        newBooking.setWorkingDay(workingDayService.findByDayOfWeekEnum(body.date().getDayOfWeek()));
        newBooking.setServiceBShop(serviceBShopService.getById(body.serviceBShopId()));
        newBooking.setDate(body.date());
        newBooking.setTime(body.time());

        return ResponseBookingDTO.fromEntity(bookingRepository.save(newBooking));
    }

    public ResponseEntity<Void> cancelBooking(String id) {
        if (!bookingRepository.existsById(id)) throw new BookingNotFound();
        bookingRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private void validateSelectedDayAndHours(LocalDate date, LocalTime time) {
        workingDayService.findByDayOfWeek(date.getDayOfWeek().getValue());
        if (date.isBefore(LocalDate.now())) throw new BookingDateInPast();
        if (!verifyHours(date.getDayOfWeek()).contains(time)) throw new BookingOutOfWorkingTimeBounds();
    }

    private String getEmailFromToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization").split(" ")[1];
        return tokenService.validateToken(token);

    }

    private List<LocalTime> verifyHours(DayOfWeek day) {
        List<Shift> shiftList = shiftService.getShiftsByDayOfWeek(day);
        List<LocalTime> availableHours = new ArrayList<>();

        for (Shift shift : shiftList) {
            LocalTime currentTime = shift.getStartTime();
            while (currentTime.isBefore(shift.getEndTime())) {
                availableHours.add(currentTime);
                currentTime = currentTime.plusHours(1);
            }
        }

        return availableHours;
    }
}
