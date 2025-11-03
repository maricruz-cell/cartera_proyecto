package com.cartera.controller;

import com.cartera.model.Persona;
import com.cartera.model.Rol;
import com.cartera.model.UsuarioRol;
import com.cartera.repository.PersonaRepository;
import com.cartera.repository.RolRepository;
import com.cartera.repository.UsuarioRolRepository;
import com.cartera.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.Optional;

@Controller
public class RegistroController {

    @Autowired
    private PersonaRepository personaRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioRolRepository usuarioRolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    // =============================
    //  Mostrar formulario
    // =============================
    @GetMapping("/registro")
    public String mostrarFormulario(Model model) {
        model.addAttribute("persona", new Persona());
        return "registro"; // registro.html
    }

    // =============================
    //  Guardar registro
    // =============================
    @PostMapping("/registro")
    public String registrarPersona(@ModelAttribute Persona persona,
                                   RedirectAttributes redirectAttributes) {

        // Verificar si ya existe CURP o correo
        Optional<Persona> existenteCurp = personaRepository.findByCurp(persona.getCurp());
        Optional<Persona> existenteCorreo = personaRepository.findByCorreo(persona.getCorreo());

        if (existenteCurp.isPresent() || existenteCorreo.isPresent()) {
            redirectAttributes.addFlashAttribute("error", "Ya existe un usuario con ese CURP o correo.");
            return "redirect:/registro";
        }

        // Generar folio automático
        long total = personaRepository.count() + 1;
        String folio = String.format("ASP2025%07d", total);
        persona.setFolio(folio);

        // Encriptar contraseña
        persona.setContrasenaBase64(passwordEncoder.encode(persona.getContrasenaBase64()));

        // Generar token y establecer cuenta inactiva
        persona.setTokenValidacion(generarToken());
        persona.setActivo(false);
        persona.setFechaCreacion(LocalDateTime.now());

        // Guardar en la BD
        personaRepository.save(persona);

        // Asignar rol ASPIRANTE
        Rol rolAspirante = rolRepository.findById(2L).orElseThrow(); // id_rol = 2
        UsuarioRol userRol = new UsuarioRol();
        userRol.setPersona(persona);
        userRol.setRol(rolAspirante);
        usuarioRolRepository.save(userRol);

        // Enviar correo de validación
        String link = "http://localhost:8081/validar/" + persona.getTokenValidacion();
        String subject = "Validación de cuenta - Cartera de Empleo GOB EDOMEX";

        String cuerpo = """
    <div style="font-family:'Arial',sans-serif;max-width:600px;margin:auto;
                border:1px solid #ddd;border-radius:8px;overflow:hidden;">

        <!-- ENCABEZADO -->
        <div style="background-color:#7B1E3D;color:#fff;padding:20px;text-align:center;">
            <h2 style="margin:0;font-size:20px;">EMPLEO GOB.EDOMEX</h2>
            <p style="margin:5px 0 0;font-size:14px;">Poder Ejecutivo</p>
        </div>

        <!-- CUERPO -->
        <div style="background:#fff;padding:40px 30px;text-align:center;color:#111;">
            <h3 style="font-size:20px;font-weight:bold;margin:0 0 20px;">
                BIENVENIDO (A) AL<br>SISTEMA DE CARTERA DE EMPLEO:
            </h3>

            <p style="font-size:15px;line-height:1.6;">
                Gracias por registrarte, <b>%s</b>.
            </p>

            <p style="font-size:15px;margin:20px 0;">
                Por favor valida tu cuenta haciendo click en el siguiente enlace:
            </p>

            <a href="%s"
               style="background-color:#7B1E3D;color:white;padding:12px 28px;
                      border-radius:6px;text-decoration:none;font-weight:bold;
                      display:inline-block;margin-top:10px;">
                ACTIVAR CUENTA
            </a>

            <p style="margin-top:25px;font-size:14px;color:#333;">
                Si no te registraste, ignora este mensaje.
            </p>
        </div>

        <!-- PIE -->
        <div style="background:#F4E5D1;text-align:center;padding:10px 20px;
                    font-size:12px;color:#5d152e;">
            DIRECCIÓN GENERAL DE PERSONAL<br>
            Subdirección de Reclutamiento y Selección del Personal<br>
            Copyright © 2020–2025 Unidad de Informática. Todos los derechos reservados.
        </div>
    </div>
""".formatted(persona.getNombre(), link);

        emailService.sendEmailHtml(persona.getCorreo(), subject, cuerpo);


        redirectAttributes.addFlashAttribute("mensaje", "Registro exitoso. Revisa tu correo para validar tu cuenta.");
        return "redirect:/login";
    }

    // =============================
    //  Validar cuenta
    // =============================
    @GetMapping("/validar/{token}")
    public String validarCuenta(@PathVariable String token, RedirectAttributes redirectAttributes) {
        Optional<Persona> personaOpt = personaRepository.findByTokenValidacion(token);

        if (personaOpt.isPresent()) {
            Persona persona = personaOpt.get();
            persona.setActivo(true);
            persona.setTokenValidacion(null); // limpiar token
            personaRepository.save(persona);
            redirectAttributes.addFlashAttribute("mensaje", "Cuenta validada correctamente. Ya puedes iniciar sesión.");
        } else {
            redirectAttributes.addFlashAttribute("error", "Token no válido o cuenta ya activada.");
        }

        return "redirect:/login";
    }

    // =============================
    //  Generador de token
    // =============================
    private String generarToken() {
        SecureRandom random = new SecureRandom();
        String caracteres = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder token = new StringBuilder();
        for (int i = 0; i < 50; i++) {
            token.append(caracteres.charAt(random.nextInt(caracteres.length())));
        }
        return token.toString();
    }


    // =============================
//  Paso 1: Mostrar aviso de privacidad
// =============================
    @GetMapping("/registro/aviso")
    public String mostrarAviso() {
        return "registro-privacidad"; // Muestra la nueva vista del aviso
    }

    // =============================
//  Paso 2: Procesar aceptación
// =============================
    @PostMapping("/registro/privacidad")
    public String procesarAviso(@RequestParam("acepto") String acepto, RedirectAttributes ra) {
        if ("si".equalsIgnoreCase(acepto)) {
            return "redirect:/registro"; // Si acepta, pasa al formulario normal
        } else {
            ra.addFlashAttribute("error", "Debes aceptar el aviso de privacidad para continuar.");
            return "redirect:/registro/aviso"; // Si no acepta, se queda en el aviso
        }
    }


}
