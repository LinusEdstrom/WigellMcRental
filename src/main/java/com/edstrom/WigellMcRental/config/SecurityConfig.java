/*package com.edstrom.WigellMcRental.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Ska köra lite dependency injection här, IoC, inversion of controll
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf (csrf ->csrf.disable())
                //så saker funkar från olika domäner
                .authorizeHttpRequests(auth -> auth

                        //USER
                        .requestMatchers(HttpMethod.GET, "/api/v1/availability").hasAnyRole("USER", "ADMIN")
                        .requestMatchers(HttpMethod.POST, "/api/v1/bookings").hasRole("USER")
                        .requestMatchers(HttpMethod.PATCH, "/api/v1/bookings/{bookingId}").hasRole("USER")

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
                // I microtjänster ska det inte vara sessioner utan STATELESS
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                );
                http.httpBasic(); //Har den lite om det ska testas av nån anledning i POSTMAN
                return http.build();
    }

}

 */

