package com.mycompany.formationspring.demo.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {
    @Bean
    public SecurityFilterChain apiSecurity (HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((authorize) -> authorize
                        // permission pour tout le monde
                        .requestMatchers(new AntPathRequestMatcher("/auth/hello")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/h2")).permitAll()
                        //permission pour ceux qui ont au moins un rôle de défini
                        .requestMatchers(new AntPathRequestMatcher("/api/user/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api/admin/**")).hasRole("ADMIN")

                        .anyRequest().permitAll() // permettre toutes les autres requêtes (à ajuster selon les besoins)
                )

                .httpBasic(Customizer.withDefaults()); // activer l'authentification Basic
        // Utiliser Content-Security-Policy pour autoriser l'affichage des frames
                http.headers(headers -> headers
                        .contentSecurityPolicy(csp -> csp
                                .policyDirectives("frame-ancestors 'self'")));
        // Désactiver la protection CSRF pour les URLs de la console H2
        http.csrf(csrf -> csrf.disable());


        return http.build();
    }


    }
