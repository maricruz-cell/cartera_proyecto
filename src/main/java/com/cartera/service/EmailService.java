package com.cartera.service;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    /**
     * Enviar correo HTML con soporte UTF-8
     */
    public void sendEmailHtml(String to, String subject, String html) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");

            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(html, true);

            mailSender.send(message);
            System.out.println(" Correo enviado a: " + to);

        } catch (Exception e) {
            System.err.println(" Error al enviar correo: " + e.getMessage());
            throw new RuntimeException("No se pudo enviar el correo.");
        }
    }
}
