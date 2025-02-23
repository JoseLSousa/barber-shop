package me.dio.barber_shop_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import me.dio.barber_shop_api.model.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, String> {
    UserDetails findByEmail(String email);

    AppUser findNameByEmail(String email);

    @Query("SELECT u.id FROM AppUser u WHERE u.email = :email")
    String  findIdByEmail(@Param("email") String email);
}
