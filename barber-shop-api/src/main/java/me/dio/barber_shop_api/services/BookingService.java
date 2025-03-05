package me.dio.barber_shop_api.services;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import me.dio.barber_shop_api.dtos.booking.BookingDTO;
import me.dio.barber_shop_api.dtos.booking.BookingListDTO;
import me.dio.barber_shop_api.dtos.booking.ResponseBookingDTO;
import me.dio.barber_shop_api.exceptions.*;
import me.dio.barber_shop_api.model.*;
import me.dio.barber_shop_api.repository.ServiceBShopRepository;
import me.dio.barber_shop_api.repository.BookingRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.awt.print.Book;
import java.time.LocalTime;
import java.util.ArrayList;
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

    public Booking listById(String id) {
        return bookingRepository.findById(id)
                .orElseThrow(BookingNotFound::new);
    }

    public List<BookingListDTO> listByUser(HttpServletRequest request) {
        String email = getEmailFromToken(request);
        return bookingRepository.findBookingByAppUserId(appUserService.getUserByEmail(email).getId());
    }

    public ArrayList<LocalTime> getAvailableHours(DayOfWeek day) {
        String id = workingDayService.findByDayOfWeek(day);
        List<LocalTime> alreadyBookedTimes = bookingRepository.findBookingsByWorkingDayId(id);
        ArrayList<LocalTime> availableHours = new ArrayList<>();
        for (LocalTime lt : verifyHours(id)) {
            if (!alreadyBookedTimes.contains(lt)) {
                availableHours.add(lt);
            }
        }

        return availableHours;
    }


    public ResponseBookingDTO createBooking(BookingDTO body, HttpServletRequest request) {
        if (bookingRepository.existsByTime(body.time())) throw new BookingAlreadyExists();
        Booking newBooking = new Booking();
        newBooking.setAppUser(appUserService.getUserByEmail(getEmailFromToken(request)));
        newBooking.setWorkingDay(workingDayService.findById(body.workingDayId()));
        newBooking.setServiceBShop(serviceBShopService.getById(body.serviceBShopId()));
        if (!verifyHours(body.workingDayId()).contains(body.time()))
            throw new BookingOutOfWorkingTimeBounds();

        newBooking.setTime(body.time());
        return ResponseBookingDTO.fromEntity(bookingRepository.save(newBooking));

    }

    public ResponseEntity<Void> cancelBooking(String id) {
        if(!bookingRepository.existsById(id)) throw new BookingNotFound();
        bookingRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    private String getEmailFromToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization").split(" ")[1];
        return tokenService.validateToken(token);

    }

    private ArrayList<LocalTime> verifyHours(String id) {
        List<Shift> shiftList = shiftService.getShiftsByWorkingDayId(id);
        ArrayList<LocalTime> availableHours = new ArrayList<>();
        LocalTime currentTime;
        for (Shift s : shiftList) {
            currentTime = s.getStartTime();
            while (currentTime.isBefore(s.getEndTime())) {
                availableHours.add(currentTime);
                currentTime = currentTime.plusHours(1);
            }
        }
        return availableHours;
    }


}
