package com.cartera.model.reclutamiento;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AspiranteMatchDto {
    private Long idAspirante;
    private String nombre;
    private int porcentaje;
    private String motivo;
}
