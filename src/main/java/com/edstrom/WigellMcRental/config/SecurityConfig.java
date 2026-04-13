package com.edstrom.WigellMcRental.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    // Ska köra lite dependency injection här, IoC, inversion of controll
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf (csrf -> csrf.disable())
                .sessionManagement(sm ->sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth

                        //USER
                        .requestMatchers(HttpMethod.GET, "/api/v1/availability").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/bookings").hasRole("USER")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/bookings/{bookingId}").hasAnyRole("USER", "ADMIN")

                        //ADMIN
                        .requestMatchers("/api/v1/customers/**").hasRole("ADMIN")
                        .requestMatchers("/api/v1/bikes/**").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/bookings").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.GET, "/api/v1/bookings/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PUT, "/api/v1/bookings/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.DELETE, "/api/v1/bookings/*").hasRole("ADMIN")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/bookings/*").hasRole("ADMIN")

                        //PUBLIC HEALTH & INFO ACTUATORS
                        .requestMatchers("/actuator/health/**", "/actuator/info/**").permitAll()

                        .anyRequest().authenticated()
                        )
                        .oauth2ResourceServer(oauth -> oauth.jwt(
                                jwt -> jwt.jwtAuthenticationConverter(jwtAuthConverter())))
                                .exceptionHandling(e -> e
                                        .authenticationEntryPoint(new BearerTokenAuthenticationEntryPoint())
                        );
                return http.build();
    }

    public Converter<Jwt, ? extends AbstractAuthenticationToken> jwtAuthConverter() {
        return jwt -> {
            Collection<GrantedAuthority> authorities = new ArrayList<>();

            //Roller i realmen
            Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_acess");
            if(realmAccess !=null && realmAccess.get("roles") instanceof Collection<?> rawRoles){
                for(Object r : rawRoles) {
                    String role = String.valueOf(r).toUpperCase();
                    authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
                }
            }
            return new JwtAuthenticationToken(jwt, authorities);
        };
    }

}



