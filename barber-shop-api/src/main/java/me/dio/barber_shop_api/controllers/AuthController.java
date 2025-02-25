package me.dio.barber_shop_api.controllers;

import lombok.AllArgsConstructor;
import me.dio.barber_shop_api.services.AppUserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import me.dio.barber_shop_api.dtos.appUser.RequestLoginDTO;
import me.dio.barber_shop_api.dtos.appUser.ResponseLoginDTO;
import me.dio.barber_shop_api.dtos.appUser.RequestRegisterDTO;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {

    private final AppUserService service;

    @PostMapping("/login")
    public ResponseEntity<ResponseLoginDTO> login(@RequestBody @Valid RequestLoginDTO data) {
        return ResponseEntity.ok(service.login(data));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RequestRegisterDTO data) {
        service.register(data);
        return ResponseEntity.ok().build();
    }

}
