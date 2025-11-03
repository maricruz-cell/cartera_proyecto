package com.cartera.controller;

import com.cartera.model.Persona;
import com.cartera.repository.PersonaRepository;
import com.cartera.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.SecureRandom;
import java.util.Optional;

@Controller
public class ForgotPasswordController {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    //Procesa el formulario del modal de “¿Olvidó su contraseña?”
    @PostMapping("/password/forgot")
    public String processForgot(String curp, RedirectAttributes ra) {
        if (curp == null || curp.trim().isEmpty()) {
            ra.addFlashAttribute("errorMsg", "Debes ingresar una CURP válida.");
            return "redirect:/login";
        }

        Optional<Persona> optionalPersona = personaRepository.findByCurp(curp.trim());
        if (optionalPersona.isEmpty()) {
            ra.addFlashAttribute("errorMsg", "No se encontró una cuenta con esa CURP.");
            return "redirect:/login";
        }

        Persona persona = optionalPersona.get();

        // Validar si tiene correo
        if (persona.getCorreo() == null || persona.getCorreo().isBlank()) {
            ra.addFlashAttribute("errorMsg", "No hay un correo registrado para esta cuenta.");
            return "redirect:/login";
        }

        // Validar si la cuenta está activa
        if (Boolean.FALSE.equals(persona.getActivo())) {
            ra.addFlashAttribute("errorMsg", "Tu cuenta se encuentra inactiva.");
            return "redirect:/login";
        }

        // Generar contraseña temporal segura
        String temporal = generarPasswordTemporal(10);

        // Encriptar con BCrypt
        String hash = passwordEncoder.encode(temporal);
        persona.setContrasenaBase64(hash);
        personaRepository.save(persona);

        // Enviar correo HTML
        try {
            String subject = "Recuperación de contraseña - Cartera de Empleo GOB EDOMEX";

            String html = """
    <div style="font-family:'Arial',sans-serif;max-width:600px;margin:auto;
                border:1px solid #ddd;border-radius:8px;overflow:hidden;">

        <!-- ENCABEZADO -->
        <div style="background-color:#7B1E3D;color:#fff;padding:20px;text-align:center;">
            <h2 style="margin:0;font-size:20px;">EMPLEO GOB.EDOMEX</h2>
            <p style="margin:5px 0 0;font-size:14px;">Poder Ejecutivo</p>
        </div>

        <!-- CUERPO -->
        <div style="background:#fff;padding:35px 25px;text-align:center;color:#222;">
            <p style="margin-top:0;font-size:15px;line-height:1.6;">
                ESTIMADO (A): <b>%s</b>
            </p>

            <p style="font-size:15px;line-height:1.6;margin:20px 0;">
                Recientemente has solicitado restablecer tu contraseña y te enviamos una nueva 
                que funciona para que puedas entrar a tu perfil de <b>CARTERA DE EMPLEO</b>.
            </p>

            <p style="font-size:16px;font-weight:bold;margin-bottom:10px;">NUEVA CONTRASEÑA:</p>
            <p style="font-size:22px;font-weight:bold;background:#f5f5f5;padding:10px 20px;
                      border-radius:6px;display:inline-block;letter-spacing:1px;">
                %s
            </p>

            <p style="margin-top:25px;font-size:14px;">
                Te recomendamos cambiar esta contraseña por una que recuerdes fácilmente.
            </p>

            <p style="margin-top:25px;font-size:14px;">
                <b>Atentamente,</b><br>
                Subdirección de Reclutamiento y Selección del Personal
            </p>

            <a href="http://localhost:8081/login"
               style="display:inline-block;margin-top:20px;background:#7B1E3D;color:#fff;
                      text-decoration:none;padding:10px 25px;border-radius:5px;font-weight:bold;">
                Ir al sistema
            </a>
        </div>

        <!-- PIE -->
        <div style="background:#F4E5D1;text-align:center;padding:10px 20px;
                    font-size:12px;color:#5d152e;">
            DIRECCIÓN GENERAL DE PERSONAL<br>
            Subdirección de Reclutamiento y Selección del Personal<br>
            Copyright © 2020–2025 Unidad de Informática. Todos los derechos reservados.
        </div>
    </div>
""".formatted(persona.getNombre(), temporal);

            emailService.sendEmailHtml(persona.getCorreo(), subject, html);
            ra.addFlashAttribute("successMsg",
                    "Se envió una contraseña temporal al correo registrado.");

        } catch (Exception e) {
            ra.addFlashAttribute("errorMsg", "Error al enviar el correo: " + e.getMessage());
        }

        return "redirect:/login";
    }

    //Genera contraseñas seguras con letras, números y símbolos
    private String generarPasswordTemporal(int length) {
        String caracteres = "ABCDEFGHJKLMNPQRSTUVWXYZabcdefghijkmnpqrstuvwxyz23456789@#$";
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(caracteres.charAt(random.nextInt(caracteres.length())));
        }
        return sb.toString();
    }
}
