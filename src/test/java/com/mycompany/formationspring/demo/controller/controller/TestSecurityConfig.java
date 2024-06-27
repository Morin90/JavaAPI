package com.mycompany.formationspring.demo.controller.controller;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@TestConfiguration
@EnableWebSecurity
public class TestSecurityConfig {

    @Bean
    @Primary
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorize) -> authorize
                .anyRequest().permitAll());
        // Utiliser Content-Security-Policy pour autoriser l'affichage des frames
        http.headers(headers -> headers
                .contentSecurityPolicy(csp -> csp
                        .policyDirectives("frame-ancestors 'self'")));
        // Désactiver la protection CSRF pour les URLs de la console H2
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }
}
