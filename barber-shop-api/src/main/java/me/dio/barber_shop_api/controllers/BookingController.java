package me.dio.barber_shop_api.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import me.dio.barber_shop_api.dtos.booking.RequestBookingDTO;
import me.dio.barber_shop_api.dtos.booking.ResponseBookingDTO;
import me.dio.barber_shop_api.model.Booking;
import me.dio.barber_shop_api.services.BookingService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/bookings")
@AllArgsConstructor
public class BookingController {
    private final BookingService service;

    @PostMapping
    public ResponseEntity<ResponseBookingDTO> createBooking(@RequestBody RequestBookingDTO body, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createBooking(body, request));
    }

    @GetMapping("/available-hours")
    public ResponseEntity<List<LocalTime>> getAvailableHoursByDay(@RequestParam("day") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate day) {
        return ResponseEntity.ok(service.getAvailableHours(day));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable String id) {
        try {
            Booking booking = service.listById(id);
            return ResponseEntity.ok(booking);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listByUserId(HttpServletRequest request) {
        return ResponseEntity.ok(service.listByUserId(request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelBooking(@PathVariable String id) {
        try {
            service.cancelBooking(id);
            return ResponseEntity.ok("Agendamento cancelado com sucesso!");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
