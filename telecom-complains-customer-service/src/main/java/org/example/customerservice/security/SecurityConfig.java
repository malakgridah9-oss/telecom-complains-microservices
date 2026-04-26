package org.example.customerservice.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:4200", "http://localhost:8080", "http://localhost:8081"));
        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE", "PATCH", "OPTIONS"));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);
        configuration.setExposedHeaders(List.of("Authorization"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
                .authorizeHttpRequests(auth -> auth
                        // Swagger public
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/swagger-ui.html",
                                "/v3/api-docs/**",
                                "/webjars/**",
                                "/actuator/**",
                                "/error"
                        ).permitAll()

                        // Endpoints publics (optionnel)
                        .requestMatchers(
                                "/api/customers/with-contracts",
                                "/api/customers/without-contracts",
                                "/api/customers/*/has-contract"
                        ).hasAnyRole("ADMIN", "AGENT")  // Changé de permitAll à ADMIN/AGENT

                        // ⭐ MODIFICATION IMPORTANTE ⭐
                        // Permettre aux ADMIN et AGENT de lire les customers
                        .requestMatchers(
                                "/api/customers",
                                "/api/customers/{customerId}",
                                "/api/customers/email/{email}",
                                "/api/customers/phone/{phone}"
                        ).hasAnyRole("ADMIN", "AGENT")  // ← AJOUTER AGENT ICI

                        // ⭐ ADMIN seulement pour les opérations d'écriture
                        .requestMatchers(
                                "/api/customers/**"
                        ).hasRole("ADMIN")  // ← Garder ADMIN seulement pour POST, PUT, DELETE

                        // Comptes - ADMIN et AGENT peuvent lire
                        .requestMatchers(
                                "/api/accounts",
                                "/api/accounts/*"
                        ).hasAnyRole("ADMIN", "AGENT")

                        // Tous les autres endpoints nécessitent authentification
                        .anyRequest().authenticated()
                )
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}