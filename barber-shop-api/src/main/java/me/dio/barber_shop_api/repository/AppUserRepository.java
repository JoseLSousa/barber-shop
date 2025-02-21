package me.dio.barber_shop_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import me.dio.barber_shop_api.model.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, String> {
    UserDetails findByEmail(String email);

    AppUser findNameByEmail(String email);
}
