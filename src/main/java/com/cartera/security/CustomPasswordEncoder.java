package com.cartera.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Base64;

@Component
public class CustomPasswordEncoder implements PasswordEncoder {

    private final BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();

    @Override
    public String encode(CharSequence rawPassword) {
        // Siempre usar BCrypt para nuevos registros
        return bcrypt.encode(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        if (encodedPassword == null) return false;

        // Detectar automáticamente el tipo de codificación
        if (encodedPassword.startsWith("$2a$") || encodedPassword.startsWith("$2b$")) {
            // BCrypt
            return bcrypt.matches(rawPassword, encodedPassword);
        } else {
            // Base64 (usuarios antiguos)
            String base64Encoded = Base64.getEncoder().encodeToString(rawPassword.toString().getBytes());
            return encodedPassword.equals(base64Encoded);
        }
    }
}
