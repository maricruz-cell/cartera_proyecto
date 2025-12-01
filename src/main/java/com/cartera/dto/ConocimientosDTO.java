package com.cartera.dto;

import lombok.Data;
import java.util.Map;

@Data
public class ConocimientosDTO {

    private Long idPersona;

    // ===== IDIOMAS =====
    private String dominaOtroIdioma;        // “SI” o “NO”
    private Integer idiomaSeleccionado;     // id del idioma
    private String nivelIdioma;             // BASICO / INTERMEDIO / AVANZADO

    // ===== INFORMÁTICA =====
    // Map<Integer, String>: idInformatica → nivel
    private Map<Integer, String> nivelesInformatica;

    // ===== PERFIL ESPECIALIZADO =====
    private String descripcionEspecial;
    private String nivelEspecial;
}
