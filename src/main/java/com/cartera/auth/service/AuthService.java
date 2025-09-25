package com.cartera.auth.service;

import com.cartera.auth.model.Usuario;
import com.cartera.auth.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Generar contraseña aleatoria segura
    private String generarPasswordAleatoria() {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789@#$%&*!";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) { // longitud de 8 caracteres
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    // Registrar usuario con CURP como login
    public Usuario register(Usuario datos) {
        // Generar contraseña aleatoria
        String passGenerada = generarPasswordAleatoria();

        // Guardar encriptada en BD
        datos.setPassword(passwordEncoder.encode(passGenerada));
        datos.setPasswordCaducada(true); // obliga a cambiarla al primer login

        // Guardar la versión en claro solo para mostrarla en pantalla (NO se guarda en BD)
        datos.setTempPassword(passGenerada);

        return usuarioRepository.save(datos);
    }
    public void actualizarPassword(String curp, String nuevaPassword) {
        Usuario usuario = usuarioRepository.findByCurp(curp)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setPassword(passwordEncoder.encode(nuevaPassword));
        usuario.setPasswordCaducada(false); // ya no necesita cambio
        usuarioRepository.save(usuario);
    }

}
