package com.cartera.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MailTestController {

    private final JavaMailSender mailSender;

    @GetMapping("/test-mail")
    public String testMail() {
        try {
            SimpleMailMessage msg = new SimpleMailMessage();
            msg.setTo("tu_otro_correo@gmail.com");
            msg.setSubject("✅ Prueba de correo SMTP desde Spring Boot");
            msg.setText("Este es un correo de prueba enviado correctamente desde Spring Boot.");
            mailSender.send(msg);
            return "📨 Correo enviado correctamente.";
        } catch (Exception e) {
            e.printStackTrace();
            return "❌ Error: " + e.getMessage();
        }
    }
}
