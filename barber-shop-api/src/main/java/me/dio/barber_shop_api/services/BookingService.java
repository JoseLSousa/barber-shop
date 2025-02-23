package me.dio.barber_shop_api.services;

import lombok.AllArgsConstructor;
import me.dio.barber_shop_api.dtos.Booking.RequestBookingDTO;
import me.dio.barber_shop_api.dtos.Booking.ResponseBookingDTO;
import me.dio.barber_shop_api.model.AppUser;
import me.dio.barber_shop_api.model.BarberShopOption;
import me.dio.barber_shop_api.model.Booking;
import me.dio.barber_shop_api.model.OpeningHour;
import me.dio.barber_shop_api.repository.AppUserRepository;
import me.dio.barber_shop_api.repository.BarberShopOptionRepository;
import me.dio.barber_shop_api.repository.BookingRepository;
import me.dio.barber_shop_api.repository.OpeningHourRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class BookingService {

    private final BookingRepository repository;
    private final OpeningHourRepository openingHourRepository;
    private final TokenService tokenService;
    private final AppUserRepository appUserRepository;
    private final BarberShopOptionRepository barberShopOptionRepository;

    public List<Booking> getAll() {
        return repository.findAll();
    }

    public ResponseBookingDTO getById(String id) {
        Booking booking = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Booking Not Found"));

        return new ResponseBookingDTO(booking);
    }

    public ResponseEntity<ResponseBookingDTO> create(RequestBookingDTO body, String token) {
        String email = tokenService.validateToken(token);
        System.out.printf(email);
        if (!verifyEntities(token, body)) {
            return ResponseEntity.badRequest().build(); // Retorna HTTP 400 em caso de erro
        }

        String userId = getUserId(email);
        Booking booking = new Booking();
        booking.setAppUserId(userId);
        booking.setStartAt(body.startAt());
        booking.setEndsAt(body.endsAt());
        booking.setBarberShopOption(getOption(body.barberShopOptionId()));
        booking.setOpeningHourId(body.openingHourId());

        repository.save(booking);
        return ResponseEntity.ok(new ResponseBookingDTO(booking));
    }


    private boolean verifyEntities(String token, RequestBookingDTO body) {
        String email = tokenService.validateToken(token);
        if (email.isEmpty()) {
            System.out.println("Usuário não existe ou token inválido");
            return false;
        }

        if (!openingHourRepository.existsByOpeningTimeLessThanEqualAndClosingTimeGreaterThanEqual(body.startAt(), body.endsAt())) {
            System.out.println("Fora do intervalo");
            return false;
        }

        if (repository.existsByStartAtLessThanAndEndsAtGreaterThan(body.endsAt(), body.startAt())) {
            System.out.println("Horário já reservado");
            return false;
        }

        return true;
    }


    private BarberShopOption getOption(String id) {
        return barberShopOptionRepository.findById(id).orElse(null);
    }

    private OpeningHour getOpeningHourId(String id) {
        return openingHourRepository.findById(id).orElse(null);
    }

    private String getUserId(String email) {

        String user = appUserRepository.findIdByEmail(email);
        if (user.isEmpty()) throw new RuntimeException("Usuário não encontrado para o e-mail: " + email);
        return user;
    }
}
