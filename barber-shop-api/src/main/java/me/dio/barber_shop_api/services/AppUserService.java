package me.dio.barber_shop_api.services;

import lombok.AllArgsConstructor;
import me.dio.barber_shop_api.dtos.appUser.RequestLoginDTO;
import me.dio.barber_shop_api.dtos.appUser.RequestRegisterDTO;
import me.dio.barber_shop_api.dtos.appUser.ResponseLoginDTO;
import me.dio.barber_shop_api.exceptions.EmailAlreadyRegistered;
import me.dio.barber_shop_api.model.AppUser;
import me.dio.barber_shop_api.model.RoleEnum;
import me.dio.barber_shop_api.repository.AppUserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AppUserService {

    private final AppUserRepository repository;

    private final AuthenticationManager authManager;

    private final TokenService tokenService;

    public ResponseLoginDTO login(RequestLoginDTO dto) {
        AppUser user = repository.findNameByEmail(dto.email());
        var userPassword = new UsernamePasswordAuthenticationToken(dto.email(), dto.password());
        var authentication = authManager.authenticate(userPassword);
        var token = tokenService.generateToken((AppUser) authentication.getPrincipal());
        return ResponseLoginDTO.toDto(token, user.getName(), user.getRole().toString());
    }

    public void register(RequestRegisterDTO dto) {
        if (repository.findByEmail(dto.email()) != null) throw new EmailAlreadyRegistered();
        String encryptedPassword = new BCryptPasswordEncoder().encode(dto.password());
        AppUser user = new AppUser();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(encryptedPassword);
        user.setRole(dto.role() == null ? RoleEnum.USER : dto.role());
        repository.save(user);
    }

    public AppUser getUserByEmail(String email) {
        return repository.findAppUserByEmail(email);
    }

}
