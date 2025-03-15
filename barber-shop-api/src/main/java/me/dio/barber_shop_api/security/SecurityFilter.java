package me.dio.barber_shop_api.security;

import java.io.IOException;

import lombok.AllArgsConstructor;
import me.dio.barber_shop_api.exceptions.TokenExpiredOrUserNotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import me.dio.barber_shop_api.repository.AppUserRepository;
import me.dio.barber_shop_api.services.TokenService;

@Component
@AllArgsConstructor
public class SecurityFilter extends OncePerRequestFilter {


    private final TokenService tokenService;

    private final AppUserRepository repository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            var token = this.recoverToken(request);
            if (token != null) {
                var subject = tokenService.validateToken(token);
                UserDetails user = repository.findByEmail(subject);
                if (user == null) throw new TokenExpiredOrUserNotFound();
                var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);

            }else{
                logger.warn("Erro ao validar Token");
            }
        } catch (TokenExpiredOrUserNotFound e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token expirado ou usuário não encontrado");
            return;
        }catch (Exception e) {
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao autenticar usuário");
            return;
        }
        filterChain.doFilter(request, response);
    }

    private String recoverToken(HttpServletRequest request) {
        var authHeader = request.getHeader("Authorization");
        if (authHeader == null) {
            return null;
        }
        return authHeader.substring(7);
    }
}
