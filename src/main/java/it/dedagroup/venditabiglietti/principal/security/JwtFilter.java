package it.dedagroup.venditabiglietti.principal.security;

import it.dedagroup.venditabiglietti.principal.model.Utente;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    CallAuthorizationServer callAuthorizationServer;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authCode = request.getHeader("Authorization");
        if(authCode != null && authCode.startsWith("Bearer ")){
            String token = authCode.substring(7);
            //TODO cambiato il service adesso chiama il microservizio
            Utente u = callAuthorizationServer.getUserDetail(token);
            if(SecurityContextHolder.getContext().getAuthentication() == null){
                UsernamePasswordAuthenticationToken upat = new UsernamePasswordAuthenticationToken(u, null, u.getAuthorities());
                upat.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(upat);
            }
        }
        filterChain.doFilter(request, response);
    }
}
