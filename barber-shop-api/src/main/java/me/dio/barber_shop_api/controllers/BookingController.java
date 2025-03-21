package me.dio.barber_shop_api.controllers;

import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import me.dio.barber_shop_api.dtos.booking.BookingDTO;
import me.dio.barber_shop_api.dtos.booking.BookingListDTO;
import me.dio.barber_shop_api.dtos.booking.ResponseBookingDTO;
import me.dio.barber_shop_api.model.Booking;
import me.dio.barber_shop_api.services.BookingService;
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


    @GetMapping("/available-hours")
    public ResponseEntity<List<LocalTime>> getAvailableHoursByDay(@RequestParam("date") LocalDate date) {
        return new ResponseEntity<>(service.getAvailableHours(date), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Booking> getById(@PathVariable String id) {
        return new ResponseEntity<>(service.getById(id), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<List<BookingListDTO>> searchBookings(
            @RequestParam(value = "date", required = false) LocalDate date,
            @RequestParam(value = "direction", required = false, defaultValue = "ASC") String direction) {
        return new ResponseEntity<>(service.getBookingsByParams(date, direction), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<BookingListDTO>> listByUser(HttpServletRequest request) {
        return ResponseEntity.ok(service.listByUser(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseBookingDTO> updateBooking(@PathVariable String id, @RequestBody BookingDTO body) {
        return ResponseEntity.ok(service.updateBooking(id, body));
    }

    @PostMapping
    public ResponseEntity<ResponseBookingDTO> createBooking(@RequestBody BookingDTO body, HttpServletRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createBooking(body, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelBooking(@PathVariable String id) {
        return service.cancelBooking(id);
    }

}
