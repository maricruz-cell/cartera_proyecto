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
            var authorities = authentication.getAuthorities();
            String rol = authorities.iterator().next().getAuthority();

            switch (rol) {
                case "ROLE_ADMINISTRADOR":
                    response.sendRedirect("/admin/menu");
                    break;
                case "ROLE_RECLUTAMIENTO":
                    response.sendRedirect("/reclutamiento/menu");
                    break;
                case "ROLE_UNIDAD":
                    response.sendRedirect("/unidad/menu");
                    break;
                case "ROLE_ASPIRANTE":
                    response.sendRedirect("/aspirante/menu");
                    break;
                default:
                    response.sendRedirect("/login?error=true");
                    break;
            }
        };
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth
                        // 🔓 RUTAS PÚBLICAS (sin login)
                        .requestMatchers(
                                "/login",
                                "/registro",
                                "/registro/**",       // ✅ Permite /registro/aviso y /registro/privacidad
                                "/validar/**",
                                "/password/forgot",
                                "/css/**",
                                "/img/**",
                                "/js/**"
                        ).permitAll()

                        // 🔒 RUTAS SEGÚN ROL
                        .requestMatchers("/admin/**").hasRole("ADMINISTRADOR")
                        .requestMatchers("/reclutamiento/**").hasRole("RECLUTAMIENTO")
                        .requestMatchers("/unidad/**").hasRole("UNIDAD")
                        .requestMatchers("/aspirante/**").hasRole("ASPIRANTE")

                        // 🔒 Todo lo demás requiere autenticación
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
