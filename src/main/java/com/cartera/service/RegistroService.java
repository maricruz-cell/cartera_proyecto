
package com.cartera.service;

import com.cartera.model.RegistroDto;
import com.cartera.model.Persona;
import com.cartera.model.DtPersona;
import com.cartera.model.Rol;
import com.cartera.model.UsuarioRol;
import com.cartera.repository.DtPersonaRepository;
import com.cartera.repository.PersonaRepository;
import com.cartera.repository.RolRepository;
import com.cartera.repository.UsuarioRolRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import com.cartera.service.CurpGenerator;

import java.time.*;
import java.util.*;

@Service
@RequiredArgsConstructor
public class RegistroService {

    private final PersonaRepository personaRepository;
    private final RolRepository rolRepository;
    private final UsuarioRolRepository usuarioRolRepository;
    private final PasswordEncoder passwordEncoder;
    private final JavaMailSender mailSender;
    private final DtPersonaRepository dtPersonaRepository;
    private final CurpGenerator curpGenerator;




    @Transactional
    public void registrarAspirante(RegistroDto dto, String appBaseUrl) {

        // Validaciones
        if (!dto.getCorreo().equalsIgnoreCase(dto.getCorreoConfirmacion())) {
            throw new IllegalArgumentException("El correo de confirmación no coincide");
        }
        if (!dto.getContrasena().equals(dto.getContrasenaConfirmacion())) {
            throw new IllegalArgumentException("La contraseña de confirmación no coincide");
        }

        // 1️⃣ Edad automática
        int edad = calcularEdad(dto.getFechaNacimiento());
        dto.setEdad(edad);

        // 2️⃣ Generar CURP automática
        String curp = curpGenerator.generarCurp(dto);
        dto.setCurp(curp);

        // 3️⃣ Validar duplicados
        personaRepository.findByCurp(curp)
                .ifPresent(p -> { throw new IllegalArgumentException("La CURP ya existe"); });

        personaRepository.findByCorreo(dto.getCorreo().toLowerCase())
                .ifPresent(p -> { throw new IllegalArgumentException("El correo ya existe"); });

        // 4️⃣ Guardar en SB_PERSONA
        Persona p = new Persona();
        p.setNombre(dto.getNombre());
        p.setApellido_paterno(dto.getApellidoPaterno());
        p.setApellido_materno(dto.getApellidoMaterno());
        p.setCurp(dto.getCurp());
        p.setRfc(dto.getRfc());
        p.setCorreo(dto.getCorreo().toLowerCase());
        p.setContrasena_base64(passwordEncoder.encode(dto.getContrasena()));
        p.setActivo(false);
        p.setFecha_creacion(LocalDateTime.now());

        String folio = generarFolio();
        p.setFolio(folio);

        String token = UUID.randomUUID().toString();
        p.setTokenValidacion(token);

        p = personaRepository.save(p);

        // 5️⃣ Guardar en DT_PERSONA
        DtPersona dt = new DtPersona();
        // 🔥 RegistroService.java corregido
        dt.setIdPersona(p.getId_persona());
        dt.setFechaNacimiento(LocalDate.parse(dto.getFechaNacimiento()));
        dt.setEdad(dto.getEdad());
        dt.setIdSexo(dto.getSexo());
        dt.setIdEstadoCivil(dto.getEstadoCivil());
        dt.setIdNacionalidad(dto.getNacionalidad());
        dt.setIdEstado(dto.getIdEstado());



        dtPersonaRepository.save(dt);

        // 6️⃣ Asignar rol aspirante
        Rol rolAspirante = rolRepository.findByDesRolIgnoreCase("Aspirante")
                .orElseThrow(() -> new IllegalStateException("No existe el rol 'Aspirante'"));

        UsuarioRol ur = new UsuarioRol();
        ur.setId_persona(p.getId_persona());
        ur.setId_rol(rolAspirante.getIdRol());

        usuarioRolRepository.save(ur);

        // 7️⃣ Enviar correo
        enviarCorreoActivacion(p.getCorreo(), token, appBaseUrl);
    }



    private String generarFolio() {
        String year = String.valueOf(Year.now().getValue());
        var lista = personaRepository.findTopByYearOrderByFolioDesc(year);
        long consecutivo = 1L;
        if (!lista.isEmpty()) {
            String ultimo = lista.get(0).getFolio();
            String numStr = ultimo.substring(3 + 4);
            consecutivo = Long.parseLong(numStr) + 1;
        }
        return "ASP" + year + String.format("%07d", consecutivo);
    }

    private void enviarCorreoActivacion(String correo, String token, String appBaseUrl) {
        String link = appBaseUrl + "/activar?token=" + token;

        try {
            var mimeMessage = mailSender.createMimeMessage();
            var helper = new org.springframework.mail.javamail.MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setTo(correo);
            helper.setSubject("Activa tu cuenta - Cartera de Empleo");

            String contenidoHtml = """
            <div style="font-family: Arial, Helvetica, sans-serif; max-width: 600px; margin: 0 auto;
                        border: 1px solid #ddd; border-radius: 10px; overflow: hidden;">
                <div style="background-color: #7B1E3D; color: #fff; text-align: center; padding: 15px;">
                    <h2 style="margin: 0; font-weight: bold;">EMPLEO GOB.EDOMEX</h2>
                    <p style="margin: 0; font-size: 14px;">Poder Ejecutivo</p>
                </div>

                <div style="padding: 30px; text-align: center;">
                    <h2 style="color: #333; margin-bottom: 10px;">BIENVENIDO (A) AL<br>SISTEMA DE CARTERA DE EMPLEO:</h2>
                    <p style="font-size: 15px; color: #444;">Gracias por registrarte.</p>
                    <p style="font-size: 14px; color: #444;">Por favor valida tu cuenta haciendo clic en el siguiente enlace:</p>

                    <a href="%s"
                       style="display: inline-block; margin-top: 15px; background-color: #7B1E3D; color: white;
                              padding: 12px 24px; border-radius: 25px; text-decoration: none;
                              font-weight: bold; letter-spacing: 1px;">ACTIVAR CUENTA</a>
                </div>

                <div style="background-color: #EBDCC2; color: #333; text-align: center; font-size: 12px;
                            padding: 10px 5px;">
                    <p style="margin: 0;">DIRECCIÓN GENERAL DE PERSONAL</p>
                    <p style="margin: 0;">copyright 2020–2025 Unidad de Informática. Todos los derechos reservados</p>
                </div>
            </div>
            """.formatted(link);

            helper.setText(contenidoHtml, true); // true = HTML
            mailSender.send(mimeMessage);

        } catch (Exception e) {
            System.err.println("❌ Error al enviar correo HTML: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Transactional
    public boolean activarCuenta(String token) {
        var personaOpt = personaRepository.findByTokenValidacion(token);
        if (personaOpt.isEmpty()) return false;

        var persona = personaOpt.get();
        persona.setActivo(true);
        persona.setTokenValidacion(null);
        personaRepository.save(persona);
        return true;
    }


    private int calcularEdad(String fechaNacimientoStr) {
        LocalDate fechaNacimiento = LocalDate.parse(fechaNacimientoStr);
        return Period.between(fechaNacimiento, LocalDate.now()).getYears();
    }



}
