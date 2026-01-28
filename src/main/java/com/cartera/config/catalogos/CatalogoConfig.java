package com.cartera.catalogos.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.List;

@Data
@AllArgsConstructor
public class CatalogoConfig {

    private String codigo;
    private String nombre;
    private String clase;     // nombre completo de la entidad
    private List<String> campos;
}
