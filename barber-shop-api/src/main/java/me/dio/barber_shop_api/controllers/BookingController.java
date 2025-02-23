package me.dio.barber_shop_api.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import me.dio.barber_shop_api.dtos.Booking.RequestBookingDTO;
import me.dio.barber_shop_api.dtos.Booking.ResponseBookingDTO;
import me.dio.barber_shop_api.model.Booking;
import me.dio.barber_shop_api.services.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

@RestController
@RequestMapping("/bookings")
@AllArgsConstructor
public class BookingController {
    private final BookingService service;

    @GetMapping
    public List<Booking> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public ResponseBookingDTO getById(@PathVariable String id) {
        return service.getById(id);
    }

    @PostMapping
    public ResponseEntity<ResponseBookingDTO> create(@RequestBody RequestBookingDTO body, HttpServletRequest headers) {
        String token = headers.getHeader("Authorization").split(" ")[1];
        return service.create(body, token);

    }

}
