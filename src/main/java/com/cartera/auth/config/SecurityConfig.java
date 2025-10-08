package com.cartera.auth.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {


    // CONFIGURACIÓN DE SEGURIDAD

    @Bean
    public PasswordEncoder passwordEncoder() {
        // Temporal: sin encriptar para desarrollo (usa BCryptPasswordEncoder en producción)
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http

                .authorizeHttpRequests(auth -> auth
                        // Rutas públicas (sin iniciar sesión)
                        .requestMatchers("/login", "/registro", "/css/**", "/img/**").permitAll()

                        // Rutas privadas por rol
                        .requestMatchers("/admin/**").hasRole("ADMINISTRADOR")
                        .requestMatchers("/aspirante/**").hasRole("ASPIRANTE")
                        .requestMatchers("/reclutamiento/**").hasRole("RECLUTAMIENTO")
                        .requestMatchers("/unidad/**").hasRole("UNIDAD")

                        // Cualquier otra ruta requiere autenticación
                        .anyRequest().authenticated()
                )

                .formLogin(form -> form
                        .loginPage("/login")                 // Página personalizada de login
                        .defaultSuccessUrl("/redirectByRole", true) // Redirección automática según rol
                        .permitAll()                         // Todos pueden acceder al login
                )

                // CONFIGURACIÓN DE LOGOUT
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .invalidateHttpSession(true)
                        .clearAuthentication(true)
                        .permitAll()
                )


                // DESACTIVAR CSRF (opcional si usas formularios simples)
                .csrf(csrf -> csrf.disable());

        return http.build();
    }
}
