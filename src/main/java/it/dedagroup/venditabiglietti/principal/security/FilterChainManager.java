package it.dedagroup.venditabiglietti.principal.security;

import it.dedagroup.venditabiglietti.principal.model.Ruolo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class FilterChainManager {

    @Autowired
    private JwtFilter filter;
    @Autowired
    private AuthenticationProvider provider;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.csrf(t -> t.disable())
                .authorizeHttpRequests(req -> req
                        .requestMatchers("/cliente/**").hasAnyRole(Ruolo.CLIENTE.toString(), Ruolo.VENDITORE.toString(), Ruolo.ADMIN.toString(), Ruolo.SUPER_ADMIN.toString())
                        .requestMatchers("/venditore/**").hasAnyRole(Ruolo.VENDITORE.toString(), Ruolo.ADMIN.toString(), Ruolo.SUPER_ADMIN.toString())
                        .requestMatchers("/admin/**").hasAnyRole(Ruolo.ADMIN.toString(), Ruolo.SUPER_ADMIN.toString())
                        .requestMatchers("/superAdmin/**").hasRole(Ruolo.SUPER_ADMIN.toString())
                        .anyRequest().permitAll())
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .authenticationProvider(provider)
                .sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return httpSecurity.build();
    }
}
