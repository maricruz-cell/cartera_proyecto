package com.cartera.controller;

import com.cartera.service.ForgotPasswordService;
import com.cartera.service.RecaptchaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class ForgotPasswordController {

    private final ForgotPasswordService forgotPasswordService;
    private final RecaptchaService recaptchaService;

    @GetMapping("/forgot-password")
    public String mostrarFormulario(Model model) {
        model.addAttribute("recaptchaSite", recaptchaService.getSiteKey());
        return "forgot-password";
    }

    @PostMapping("/forgot-password")
    public String procesarFormulario(
            @RequestParam("curp") String curp,
            @RequestParam("g-recaptcha-response") String captchaResponse,
            Model model
    ) {

        // Validar CAPTCHA
        if (!recaptchaService.verify(captchaResponse)) {
            model.addAttribute("error", "Debes validar el CAPTCHA.");
            model.addAttribute("recaptchaSite", recaptchaService.getSiteKey());
            return "forgot-password";
        }

        // Procesar recuperación
        String resultado = forgotPasswordService.enviarNuevaContrasena(curp);

        if (resultado.startsWith("Error")) {
            model.addAttribute("error", resultado);
        } else {
            model.addAttribute("mensaje", resultado);
        }

        model.addAttribute("recaptchaSite", recaptchaService.getSiteKey());
        return "forgot-password";
    }
}
