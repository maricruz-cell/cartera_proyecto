package com.cartera.config;

import com.cartera.service.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    @Bean
    public AuthenticationSuccessHandler successHandler() {
        return (request, response, authentication) -> {
            String rol = authentication.getAuthorities().iterator().next().getAuthority();
            System.out.println("✅ Rol autenticado: " + rol); // Para verificar

            switch (rol) {
                case "Administrador" -> response.sendRedirect("/admin/menu");
                case "Reclutamiento" -> response.sendRedirect("/reclutamiento/menu");
                case "Unidad" -> response.sendRedirect("/unidad/menu");
                case "Aspirante" -> response.sendRedirect("/aspirante/menu");
                default -> response.sendRedirect("/login?error=true");
            }
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login", "/registro/**", "/css/**", "/js/**", "/img/**").permitAll()
                        .requestMatchers("/admin/**").hasAuthority("Administrador")
                        .requestMatchers("/reclutamiento/**").hasAuthority("Reclutamiento")
                        .requestMatchers("/unidad/**").hasAuthority("Unidad")
                        .requestMatchers("/aspirante/**").hasAuthority("Aspirante")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .usernameParameter("username")
                        .passwordParameter("password")
                        .successHandler(successHandler())
                        .failureUrl("/login?error=true")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true")
                        .permitAll()
                );
        return http.build();
    }
}
