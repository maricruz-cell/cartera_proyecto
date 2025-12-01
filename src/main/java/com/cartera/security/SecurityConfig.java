package com.cartera.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.*;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;

@Configuration
@RequiredArgsConstructor
public class SecurityConfig {

    private final CustomUserDetailsService uds;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .csrf().disable()
                .authorizeHttpRequests(auth -> auth

                        // RECURSOS ESTÁTICOS (CSS, JS, IMG)
                        .requestMatchers("/css/**", "/js/**", "/img/**").permitAll()

                        // PANTALLA INICIAL (DEBE SER PÚBLICA)
                        .requestMatchers("/", "/perfil-ingreso", "/perfil-ingreso.html").permitAll()

                        // REGISTRO Y ACTIVACIÓN
                        .requestMatchers("/registro/**", "/activar/**").permitAll()
                        .requestMatchers("/forgot-password/**").permitAll()

                        // LOGIN PÚBLICO
                        .requestMatchers("/login").permitAll()

                        // RUTAS PROTEGIDAS POR ROL
                        .requestMatchers("/admin/**").hasRole("ADMINISTRADOR")
                        .requestMatchers("/aspirante/**").hasRole("ASPIRANTE")
                        .requestMatchers("/reclutamiento/**").hasRole("RECLUTAMIENTO")
                        .requestMatchers("/unidad/**").hasRole("UNIDAD")

                        // CUALQUIER OTRA RUTA, AUTENTICADA
                        .anyRequest().authenticated()
                )

                // CONFIGURACIÓN DE LOGIN PERSONALIZADO
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")   // 👈 CLAVE: evita redirecciones forzadas
                        .usernameParameter("curp")
                        .passwordParameter("contrasena")
                        .successHandler(successHandler())
                        .failureUrl("/login?error=true")
                        .permitAll()
                )

                // LOGOUT
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return (req, res, auth) -> {

            String perfil = req.getParameter("perfil"); // Viene desde perfil-ingreso.html
            Collection<? extends GrantedAuthority> roles = auth.getAuthorities();

            // --- VALIDACIONES SEGÚN EL PERFIL SELECCIONADO ---

            // Perfil “ASPIRANTE” solo permite ROLE_ASPIRANTE
            if ("aspirante".equals(perfil)) {
                boolean esAspirante = roles.stream()
                        .anyMatch(r -> r.getAuthority().equals("ROLE_ASPIRANTE"));

                if (!esAspirante) {
                    res.sendRedirect("/login?error=perfil_incorrecto");
                    return;
                }
            }

            // Perfil “COORDINACION” NO permite aspirante
            if ("coordinacion".equals(perfil)) {
                boolean esAspirante = roles.stream()
                        .anyMatch(r -> r.getAuthority().equals("ROLE_ASPIRANTE"));

                if (esAspirante) {
                    res.sendRedirect("/login?error=perfil_incorrecto");
                    return;
                }
            }

            // REDIRECCIÓN NORMAL POR ROL
            if (roles.stream().anyMatch(r -> r.getAuthority().equals("ROLE_ADMINISTRADOR"))) {
                res.sendRedirect("/admin/menu");
                return;
            }
            if (roles.stream().anyMatch(r -> r.getAuthority().equals("ROLE_ASPIRANTE"))) {
                res.sendRedirect("/aspirante/menu");
                return;
            }
            if (roles.stream().anyMatch(r -> r.getAuthority().equals("ROLE_RECLUTAMIENTO"))) {
                res.sendRedirect("/reclutamiento/menu");
                return;
            }
            if (roles.stream().anyMatch(r -> r.getAuthority().equals("ROLE_UNIDAD"))) {
                res.sendRedirect("/unidad/menu");
                return;
            }

            res.sendRedirect("/login?error=sin_rol");
        };
    }

    @Bean
    public DaoAuthenticationProvider authProvider() {
        DaoAuthenticationProvider auth = new DaoAuthenticationProvider();
        auth.setUserDetailsService(uds);
        auth.setPasswordEncoder(passwordEncoder());
        return auth;
    }
}
