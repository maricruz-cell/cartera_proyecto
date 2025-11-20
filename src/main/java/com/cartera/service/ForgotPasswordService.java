package com.cartera.service;

import com.cartera.model.Persona;
import com.cartera.repository.PersonaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.SecureRandom;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ForgotPasswordService {

    private final PersonaRepository personaRepository;
    private final JavaMailSender mailSender;
    private final PasswordEncoder passwordEncoder;

    /**
     * Procesa la recuperación de contraseña:
     *  - Busca a la persona por CURP
     *  - Genera una nueva contraseña aleatoria
     *  - La guarda cifrada en contrasena_base64
     *  - Envía un correo con la nueva contraseña en texto plano
     *
     * @param curp CURP capturada en el formulario
     * @return mensaje para mostrar en la vista (éxito o error)
     */
    @Transactional
    public String enviarNuevaContrasena(String curp) {

        // 1) Buscar persona por CURP (se fuerza a mayúsculas por si la escriben en minúsculas)
        Optional<Persona> optPersona = personaRepository.findByCurp(curp.toUpperCase());

        if (optPersona.isEmpty()) {
            return "Error: No existe un usuario registrado con esa CURP.";
        }

        Persona persona = optPersona.get();

        // 2) Obtener correo donde se enviará la contraseña
        //    Usa el que realmente estés utilizando: persona.getCorreo() o getCorreo_electronico()
        String correoDestino = persona.getCorreo();  // <-- cámbialo a getCorreo_electronico() si usas ese

        if (correoDestino == null || correoDestino.isBlank()) {
            return "Error: El usuario no tiene un correo electrónico registrado.";
        }

        // 3) Generar nueva contraseña aleatoria (10 caracteres)
        String nuevaContrasenaPlano = generarContrasenaAleatoria(10);

        // 4) Cifrar con BCrypt y guardar en la columna contrasena_base64
        String hash = passwordEncoder.encode(nuevaContrasenaPlano);
        persona.setContrasena_base64(hash);
        personaRepository.save(persona);

        // 5) Enviar correo al usuario
        try {
            SimpleMailMessage mensaje = new SimpleMailMessage();
            mensaje.setTo(correoDestino);
            mensaje.setSubject("Cartera de Empleo - Nueva contraseña");

            String cuerpo = """
                    ESTIMADO (A):

                    Recientemente has solicitado restablecer tu contraseña y te enviamos una nueva
                    que funciona para que puedas entrar a tu perfil de CARTERA DE EMPLEO.

                    NUEVA CONTRASEÑA: %s

                    Te recomendamos que cambies esta contraseña por una que recuerdes fácilmente
                    una vez que ingreses al sistema.

                    Atentamente:
                    Subdirección de reclutamiento y selección del personal
                    Dirección General de Personal
                    """.formatted(nuevaContrasenaPlano);

            mensaje.setText(cuerpo);

            mailSender.send(mensaje);

        } catch (Exception e) {
            // Si falla el envío, dejamos el hash guardado, pero avisamos del error
            return "Error: No fue posible enviar el correo. Inténtalo más tarde.";
        }

        // 6) Mensaje de éxito
        return "Se ha enviado una nueva contraseña al correo registrado: " + correoDestino;
    }

    /**
     * Genera una contraseña aleatoria usando letras y números.
     */
    private String generarContrasenaAleatoria(int longitud) {
        String caracteres = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz23456789";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(longitud);

        for (int i = 0; i < longitud; i++) {
            int idx = random.nextInt(caracteres.length());
            sb.append(caracteres.charAt(idx));
        }
        return sb.toString();
    }
}
