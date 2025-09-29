package com.cartera.auth.service;

import com.cartera.auth.model.Usuario;
import com.cartera.auth.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.cartera.auth.model.Direccion;


@Service
public class AuthService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // Registrar usuario con CURP como login
    public Usuario register(Usuario datos) {
        // Generar una contraseña temporal
        String passDefault = generarPasswordTemporal();

        // Guardar la contraseña encriptada en la BD
        datos.setPassword(passwordEncoder.encode(passDefault));
        datos.setPasswordCaducada(true);

        // Guardar la contraseña temporal (NO se persiste en BD)
        datos.setTempPassword(passDefault);

        return usuarioRepository.save(datos);
    }

    // Generar contraseña temporal aleatoria
    private String generarPasswordTemporal() {
        return "EMP" + (int)(Math.random() * 100000); // Ejemplo: EMP12345
    }

    // Buscar usuario por CURP
    public Usuario findByUsername(String curp) {
        return usuarioRepository.findByCurp(curp).orElse(null);
    }

    // Actualizar contraseña y marcar como válida
    public void actualizarPassword(String curp, String nuevaPassword) {
        Usuario usuario = usuarioRepository.findByCurp(curp)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        usuario.setPassword(passwordEncoder.encode(nuevaPassword));
        usuario.setPasswordCaducada(false); // ya no necesita cambio
        usuarioRepository.save(usuario);
    }

    // Guardar o actualizar dirección
    public void actualizarDireccion(String curp, Direccion direccion) {
        Usuario usuario = usuarioRepository.findByCurp(curp)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        usuario.setDireccion(direccion);
        usuarioRepository.save(usuario); // en memoria se reemplaza
    }
    public void actualizarUsuario(Usuario usuario) {
        usuarioRepository.save(usuario);
    }



}
