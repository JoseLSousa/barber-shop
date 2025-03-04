package me.dio.barber_shop_api.services;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import me.dio.barber_shop_api.dtos.booking.RequestBookingDTO;
import me.dio.barber_shop_api.dtos.booking.RequestBookingListByUserDTO;
import me.dio.barber_shop_api.dtos.booking.ResponseBookingDTO;
import me.dio.barber_shop_api.exceptions.*;
import me.dio.barber_shop_api.model.AppUser;
import me.dio.barber_shop_api.model.Booking;
import me.dio.barber_shop_api.model.ServiceBShop;
import me.dio.barber_shop_api.model.WorkingDay;
import me.dio.barber_shop_api.repository.AppUserRepository;
import me.dio.barber_shop_api.repository.ServiceBShopRepository;
import me.dio.barber_shop_api.repository.BookingRepository;
import me.dio.barber_shop_api.repository.WorkingDayRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;
    private final WorkingDayRepository workingDayRepository;
    private final TokenService tokenService;
    private final AppUserRepository appUserRepository;
    private final ServiceBShopRepository serviceBShopRepository;

    public Booking listById(String id) {
        return bookingRepository.findById(id)
                .orElseThrow(BookingNotFound::new);
    }

    public List<RequestBookingListByUserDTO> listByUserId(HttpServletRequest request) {
        String userId = appUserExists(getEmailFromToken(request)).getId();
        return bookingRepository.findBookingByAppUserId(userId);
    }

    public List<LocalTime> getAvailableHours(String date) {
        WorkingDay workingDay = workingDayRepository.findById(date).orElseThrow(WorkingDayNotFound::new);
        // Horários já reservados
        ArrayList<LocalTime> bookedHours = bookingRepository.findBookingByWorkingDayId(workingDay.getId());
        // Lista dos horários disponíveis do dia selecionado
        ArrayList<LocalTime> availableHours = new ArrayList<>();
        LocalTime currentTime = workingDay.getOpeningTime();

        while (currentTime.isBefore(workingDay.getClosingTime().plusHours(1))) {
            if (!bookedHours.contains(currentTime)) {
                availableHours.add(currentTime);
            }
            currentTime = currentTime.plusHours(1);
        }
        return availableHours;
    }

    public ResponseEntity<Void> cancelBooking(String id) {
        bookingExists(id);
        bookingRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    public ResponseBookingDTO createBooking(RequestBookingDTO body, HttpServletRequest request) {
        bookingExistsByTime(body.time()); // Verifica se já tem agendamento naquela hora
        Booking newBooking = new Booking();
        newBooking.setAppUser(appUserExists(getEmailFromToken(request)));
        newBooking.setServiceBShop(serviceExists(body.serviceBShopId()));
        newBooking.setTime(body.time());
        newBooking.setWorkingDay(workingDayExists(body.workingDayId(), body.time()));
        return ResponseBookingDTO.fromEntity(bookingRepository.save(newBooking));

    }

    private void bookingExists(String id) {
        if (!bookingRepository.existsById(id)) throw new BookingNotFound();
    }

    private void bookingExistsByTime(LocalTime time) {
        if (bookingRepository.existsByTime(time)) throw new BookingAlreadyExists();
    }

    private WorkingDay workingDayExists(String id, LocalTime time) {
        return workingDayRepository.findValidWorkingDay(id, time).orElseThrow(BookingOutOfWorkingTimeBounds::new);
    }

    private ServiceBShop serviceExists(String id) {
        return serviceBShopRepository.findById(id).orElseThrow(ServiceBShopNotFound::new);
    }

    private AppUser appUserExists(String email) {
        AppUser user = appUserRepository.findAppUserByEmail(email);
        if (user == null) throw new UsernameNotFoundException("");
        return user;
    }

    private String getEmailFromToken(HttpServletRequest request) {
        String token = request.getHeader("Authorization").split("Bearer ")[1];
        return tokenService.validateToken(token);
    }

}
