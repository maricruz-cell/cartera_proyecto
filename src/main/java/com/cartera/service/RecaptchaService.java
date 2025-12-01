
package com.cartera.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RecaptchaService {

    @Value("${recaptcha.secret}")
    private String secret;

    @Value("${recaptcha.site}")
    private String siteKey;

    private final RestTemplate restTemplate = new RestTemplate();

    // 👉 ESTE ES EL MÉTODO QUE FALTABA
    public String getSiteKey() {
        return siteKey;
    }

    // (Opcional) validar el token con Google
    public boolean verify(String token) {
        // Si quieres algo rápido mientras, puedes regresar siempre true:
        // return true;

        String url = "https://www.google.com/recaptcha/api/siteverify" +
                "?secret=" + secret +
                "&response=" + token;

        RecaptchaResponse response =
                restTemplate.postForObject(url, null, RecaptchaResponse.class);

        return response != null && response.isSuccess();
    }

    // Clase interna para mapear la respuesta de Google
    private static class RecaptchaResponse {
        private boolean success;

        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }
    }
}
