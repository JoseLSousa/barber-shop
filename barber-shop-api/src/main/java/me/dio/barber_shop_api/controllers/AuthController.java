package me.dio.barber_shop_api.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import me.dio.barber_shop_api.dtos.AuthDTO;
import me.dio.barber_shop_api.dtos.LoginResponseDTO;
import me.dio.barber_shop_api.dtos.RegisterDTO;
import me.dio.barber_shop_api.model.AppUser;
import me.dio.barber_shop_api.repository.AppUserRepository;
import me.dio.barber_shop_api.services.TokenService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private AppUserRepository repository;
    @Autowired
    private TokenService tokenService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody @Valid AuthDTO data) {
        var name = repository.findNameByEmail(data.email());
        var userPassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var authentication = authenticationManager.authenticate(userPassword);
        var token = tokenService.generateToken((AppUser) authentication.getPrincipal());
        return ResponseEntity.ok(new LoginResponseDTO(token, name.getName()));
    }

    @PostMapping("/register")
    public ResponseEntity<Void> register(@RequestBody @Valid RegisterDTO data) {
        if (repository.findByEmail(data.email()) != null)
            return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());

        AppUser user = new AppUser(data.name(), data.email(), encryptedPassword, data.role());

        repository.save(user);

        return ResponseEntity.ok().build();
    }

}
